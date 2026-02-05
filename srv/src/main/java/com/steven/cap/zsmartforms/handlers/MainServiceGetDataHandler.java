package com.steven.cap.zsmartforms.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.Upsert;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.db.entity.customer.Customer;
import cds.gen.db.entity.customer.Customer_;
import cds.gen.db.entity.header.Header;
import cds.gen.db.entity.header.Header_;
import cds.gen.db.entity.item.Item;
import cds.gen.db.entity.item.Item_;
import cds.gen.db.entity.materialtype.MaterialType;
import cds.gen.db.entity.materialtype.MaterialType_;
import cds.gen.mainservice.MainService_;

/**
 * Event handler for MainService that fetches and synchronizes data from DM4 system.
 * Processes headers, items, material types, and customers, inserting new records into the database.
 */
@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServiceGetDataHandler implements EventHandler {

    private final PersistenceService db;

    /**
     * Constructor to inject the PersistenceService for database operations.
     * @param db PersistenceService instance for running queries
     */
    public MainServiceGetDataHandler(PersistenceService db) {
        this.db = db;
    }

    /**
     * Handles the READ event by fetching data from DM4 system and synchronizing it with the database.
     * Only inserts new records that don't already exist to avoid duplicates.
     * 
     * @param context CdsReadEventContext containing event information
     * @throws ServiceException if an error occurs during data fetch or processing
     */
    @Before(event = CqnService.EVENT_READ)
    void getListReportData(CdsReadEventContext context) {

        // Fetch all existing records from the database to check for duplicates
        List<Header> allExistingHeaders = db.run(
                Select.from(Header_.CDS_NAME)).listOf(Header.class);

        List<Item> allExistingItems = db.run(
                Select.from(Item_.CDS_NAME)).listOf(Item.class);

        List<Customer> allExistingCustomers = db.run(
            Select.from(Customer_.CDS_NAME)).listOf(Customer.class);

        // Maps to store existing records for O(1) lookup time
        Map<String, Boolean> allExistingDocumentNumbers = new HashMap<>();
        Map<String, Map<String, Boolean>> allExistingDocumentNumberItems = new HashMap<>();
        Map<String,Boolean> allExistingCustomerNumbers = new HashMap<>();

        // Populate document number lookup map
        allExistingHeaders.stream()
                .map(Header::getDocumentNumber)
                .forEach(docNum -> allExistingDocumentNumbers.put(docNum, true));

        // Populate document-item lookup map for duplicate checking
        allExistingItems.stream()
                .forEach(item -> {
                    String docNum = item.getItemNumber();
                    allExistingDocumentNumberItems
                            .computeIfAbsent(docNum, k -> new HashMap<>())
                            .put(item.getItemNumber(), true);
                });

        // Populate customer number lookup map
        allExistingCustomers.stream()
                .map(Customer::getCustomerNumber)
                .forEach(custNum -> allExistingCustomerNumbers.put(custNum, true));

        try {
            // Fetch data from external DM4 system
            Map<String, NodeList> dataMap = GetDataHandler.getDataFromDM4System();

            // Process HEADER entities
            NodeList headerList = dataMap.getOrDefault("HEADER", null);
            if (headerList != null) {
                List<Header> listOfHeaders = new ArrayList<Header>();
                for (int i = 0; i < headerList.getLength(); i++) {
                    Element header = (Element) headerList.item(i);
                    // Skip if header already exists in database
                    if (allExistingDocumentNumbers.containsKey(
                            getTagValue(header, "VBELN"))) {
                        continue;
                    }
                    // Create new header entity from XML data
                    Header newHeader = CreateEntityHandler.createDocument(
                            getTagValue(header, "VBELN"),
                            getTagValue(header, "INCO1"),
                            getTagValue(header, "INCO2"),
                            getTagValue(header, "ROUTE"),
                            getTagValue(header, "LFDAT"),
                            getTagValue(header, "WADAT"),
                            getTagValue(header, "WAUHR"),
                            getTagValue(header, "KUNAG"),
                            getTagValue(header, "KUNNR"));
                    listOfHeaders.add(newHeader);
                }
                // Bulk insert new headers into database
                db.run(Upsert.into(Header_.CDS_NAME).entries(listOfHeaders));
            }

            // Process ITEM entities
            NodeList itemLists = dataMap.getOrDefault("ITEMS", null);
            if (itemLists != null) {
                List<Item> listOfItems = new ArrayList<Item>();
                for (int i = 0; i < itemLists.getLength(); i++) {
                    Element item = (Element) itemLists.item(i);
                    // Skip if item already exists for this document
                    if (allExistingDocumentNumberItems.containsKey(getTagValue(item, "VBELN"))
                            && allExistingDocumentNumberItems.get(getTagValue(item, "VBELN"))
                                    .containsKey(getTagValue(item, "POSNR"))) {
                        continue;
                    }
                    // Create new item entity from XML data
                    Item newItem = CreateEntityHandler.createDocumentItems(
                            getTagValue(item, "VBELN"),
                            getTagValue(item, "POSNR"),
                            getTagValue(item, "MATNR"),
                            getTagValue(item, "MTART"),
                            getTagValue(item, "LFIMG"),
                            getTagValue(item, "LGMNG"),
                            getTagValue(item, "ARKTX"),
                            getTagValue(item, "MEINS"));
                    listOfItems.add(newItem);
                }
                // Bulk insert new items into database
                db.run(Upsert.into(Item_.CDS_NAME).entries(listOfItems));
            }

            // Process MATERIAL_TYPE entities
            NodeList materialTypeLists = dataMap.getOrDefault("MATERIAL_TYPES", null);
            if (materialTypeLists != null) {
                List<MaterialType> listOfMaterialTypes = new ArrayList<MaterialType>();
                for (int i = 0; i < materialTypeLists.getLength(); i++) {
                    Element materialType = (Element) materialTypeLists.item(i);
                    // Create new material type entity from XML data
                    MaterialType newMaterialType = CreateEntityHandler.createMaterialType(
                            getTagValue(materialType, "MTART"),
                            getTagValue(materialType, "MTBEZ"));
                    listOfMaterialTypes.add(newMaterialType);
                }
                // Bulk insert material types into database
                db.run(Upsert.into(MaterialType_.CDS_NAME).entries(listOfMaterialTypes));
            }

            // Process CUSTOMER entities
            NodeList customerLists = dataMap.getOrDefault("CUSTOMERS", null);
            if (customerLists != null) {
                List<Customer> listOfCustomers = new ArrayList<Customer>();
                for (int i = 0; i < customerLists.getLength(); i++) {
                    Element customer = (Element) customerLists.item(i);
                    // Skip if customer already exists in database
                    if (allExistingCustomerNumbers.containsKey(
                            getTagValue(customer, "KUNNR"))) {
                        continue;
                    }
                    // Create new customer entity from XML data
                    Customer newCustomer = CreateEntityHandler.createCustomer(
                            getTagValue(customer, "KUNNR"),
                            getTagValue(customer, "NAME1"),
                            getTagValue(customer, "TELF1"),
                            getTagValue(customer, "PSTLZ"),
                            getTagValue(customer, "STRAS"));
                    listOfCustomers.add(newCustomer);
                }
                // Bulk insert new customers into database
                db.run(Upsert.into(Customer_.CDS_NAME).entries(listOfCustomers));
            }
        } catch (Exception e) {
            throw new ServiceException("Error fetching data: " + e.getMessage());
        }
    }

    /**
     * Utility method to extract tag value from an XML element.
     * 
     * @param parent The parent XML element
     * @param tagName The tag name to search for
     * @return The text content of the first matching tag, or null if not found
     */
    private static String getTagValue(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() == 0) {
            return null;
        }
        return list.item(0).getTextContent();
    }
}
