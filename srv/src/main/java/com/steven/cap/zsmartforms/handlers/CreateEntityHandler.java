package com.steven.cap.zsmartforms.handlers;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import cds.gen.db.entity.customer.Customer;
import cds.gen.db.entity.document.Document;
import cds.gen.db.entity.header.Header;
import cds.gen.db.entity.item.Item;
import cds.gen.db.entity.materialtype.MaterialType;

public class CreateEntityHandler {

    public static Header createDocument(String documentNumber,
                        String incoterms1,
                        String incoterms2,
                        String route,
                        String deliveryDate,
                        String goodsIssueDate,
                        String goodsIssueTime,
                        String soldToParty,
                        String shipToParty
    ) {
        Header newDocument = Header.create();
        newDocument.setDocumentNumber(documentNumber);
        newDocument.setIncoterms1(incoterms1);
        newDocument.setIncoterms2(incoterms2);
        newDocument.setRoute(route);
        newDocument.setDeliveryDate(parseDate(deliveryDate));
        newDocument.setGoodsIssueDate(parseDate(goodsIssueDate));
        newDocument.setGoodsIssueTime(parseTime(goodsIssueTime));
        newDocument.setSoldToPartyCustomerNumber(soldToParty);
        newDocument.setShipToPartyCustomerNumber(shipToParty);
        return newDocument;
    }

    static LocalDate parseDate(String dateString) {
        if ("0000-00-00".equals(dateString)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }

    static LocalTime parseTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH);
        LocalTime time = LocalTime.parse(timeString, formatter);
        return time;
    }

    public static Item createDocumentItems(String headerDocumentNumber,
                            String itemNumber,
                            String material,
                            String materialTypeMaterialType,
                            String quantityDeliveredSales,
                            String quantityDeliveredStockKeeping,
                            String shortText,
                            String uom) {
        Item newItem = Item.create();
        newItem.setHeaderDocumentNumber(headerDocumentNumber);
        newItem.setItemNumber(itemNumber);
        newItem.setMaterial(material);
        newItem.setMaterialTypeLanguageCode("EN");
        newItem.setMaterialTypeMaterialType(materialTypeMaterialType);
        newItem.setQuantityDeliveredSales(new BigDecimal(quantityDeliveredSales));
        newItem.setQuantityDeliveredStockKeeping(new BigDecimal(quantityDeliveredStockKeeping));
        newItem.setShortText(shortText);
        newItem.setUom(uom);
        return newItem;
    }

    public static MaterialType createMaterialType(String materialType, String description) {
        MaterialType newMaterialType = MaterialType.create();
        newMaterialType.setLanguageCode("EN");
        newMaterialType.setMaterialType(materialType);
        newMaterialType.setMaterialTypeDescription(description);
        return newMaterialType;
    }

    public static Customer createCustomer(String customerNumber, 
                        String name,
                        String phoneNumber,
                        String postalCode,
                        String street) {
        Customer newCustomer = Customer.create();
        newCustomer.setCustomerNumber(customerNumber);
        newCustomer.setPhoneNumber(phoneNumber);
        newCustomer.setPostalCode(postalCode);
        newCustomer.setStreet(street);
        newCustomer.setName(name);
        return newCustomer;
    }

    public static Document createDocument(String documentNumber,
                                        InputStream documentData) {
        Document newDocument = Document.create();
        newDocument.setHeaderDocumentNumber(documentNumber);
        String documentName = "lunchyDocument_" + documentNumber;
        newDocument.setName(documentName);
        newDocument.setDocumentData(documentData);
        return newDocument;
    }

}
