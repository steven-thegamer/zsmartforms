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
import cds.gen.db.types.DocumentStatus;
import cds.gen.mainservice.MainService_;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServiceGetDataHandler implements EventHandler {

    private final PersistenceService db;

    // Inject the PersistenceService to run queries
    public MainServiceGetDataHandler(PersistenceService db) {
        this.db = db;
    }

    @Before(event = CqnService.EVENT_READ)
    void getListReportData(CdsReadEventContext context) {

        List<Header> allExistingHeaders = db.run(
                Select.from(Header_.CDS_NAME)).listOf(Header.class);

        List<Item> allExistingItems = db.run(
                Select.from(Item_.CDS_NAME)).listOf(Item.class);

        List<Customer> allExistingCustomers = db.run(
            Select.from(Customer_.CDS_NAME)).listOf(Customer.class);

        Map<String, Boolean> allExistingDocumentNumbers = new HashMap<>();
        Map<String, Map<String, Boolean>> allExistingDocumentNumberItems = new HashMap<>();
        Map<String,Boolean> allExistingCustomerNumbers = new HashMap<>();


        allExistingHeaders.stream()
                .map(Header::getDocumentNumber)
                .forEach(docNum -> allExistingDocumentNumbers.put(docNum, true));

        allExistingItems.stream()
                .forEach(item -> {
                    String docNum = item.getItemNumber();
                    allExistingDocumentNumberItems
                            .computeIfAbsent(docNum, k -> new HashMap<>())
                            .put(item.getItemNumber(), true);
                });

        allExistingCustomers.stream()
                .map(Customer::getCustomerNumber)
                .forEach(custNum -> allExistingCustomerNumbers.put(custNum, true));

        System.out.println("CQN data: " + context.getCqn().toString());
        try {
            Map<String, NodeList> dataMap = GetDataHandler.getDataFromDM4System();
            NodeList headerList = dataMap.getOrDefault("HEADER", null);
            if (headerList != null) {
                List<Header> listOfHeaders = new ArrayList<Header>();
                for (int i = 0; i < headerList.getLength(); i++) {
                    Element header = (Element) headerList.item(i);
                    if (allExistingDocumentNumbers.containsKey(
                            getTagValue(header, "VBELN"))) {
                        continue;
                    }
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
                    newHeader.setStatus(DocumentStatus.SYNCED);
                    listOfHeaders.add(newHeader);
                }
                db.run(Upsert.into(Header_.CDS_NAME).entries(listOfHeaders));
            }
            NodeList itemLists = dataMap.getOrDefault("ITEMS", null);
            if (itemLists != null) {
                List<Item> listOfItems = new ArrayList<Item>();
                for (int i = 0; i < itemLists.getLength(); i++) {
                    Element item = (Element) itemLists.item(i);
                    if (allExistingDocumentNumberItems.containsKey(getTagValue(item, "VBELN"))
                            && allExistingDocumentNumberItems.get(getTagValue(item, "VBELN"))
                                    .containsKey(getTagValue(item, "POSNR"))) {
                        continue;
                    }

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
                db.run(Upsert.into(Item_.CDS_NAME).entries(listOfItems));
            }
            NodeList materialTypeLists = dataMap.getOrDefault("MATERIAL_TYPES", null);
            if (materialTypeLists != null) {
                List<MaterialType> listOfMaterialTypes = new ArrayList<MaterialType>();
                for (int i = 0; i < materialTypeLists.getLength(); i++) {
                    Element materialType = (Element) materialTypeLists.item(i);
                    MaterialType newMaterialType = CreateEntityHandler.createMaterialType(
                            getTagValue(materialType, "MTART"),
                            getTagValue(materialType, "MTBEZ"));
                    listOfMaterialTypes.add(newMaterialType);
                }
                db.run(Upsert.into(MaterialType_.CDS_NAME).entries(listOfMaterialTypes));
            }
            NodeList customerLists = dataMap.getOrDefault("CUSTOMERS", null);
            if (customerLists != null) {
                List<Customer> listOfCustomers = new ArrayList<Customer>();
                for (int i = 0; i < customerLists.getLength(); i++) {
                    Element customer = (Element) customerLists.item(i);
                    if (allExistingCustomerNumbers.containsKey(
                            getTagValue(customer, "KUNNR"))) {
                        continue;
                    }
                    Customer newCustomer = CreateEntityHandler.createCustomer(
                            getTagValue(customer, "KUNNR"),
                            getTagValue(customer, "NAME1"),
                            getTagValue(customer, "TELF1"),
                            getTagValue(customer, "PSTLZ"),
                            getTagValue(customer, "STRAS"));
                    listOfCustomers.add(newCustomer);
                }
                db.run(Upsert.into(Customer_.CDS_NAME).entries(listOfCustomers));
            }
        } catch (Exception e) {
            throw new ServiceException("Error fetching data: " + e.getMessage());
        }
    }

    private static String getTagValue(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() == 0) {
            return null;
        }
        return list.item(0).getTextContent();
    }
}
