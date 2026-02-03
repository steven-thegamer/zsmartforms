package com.steven.cap.zsmartforms.handlers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import cds.gen.db.entity.customer.Customer;
import cds.gen.db.entity.header.Header;
import cds.gen.db.entity.materialtype.MaterialType;

public class CreateEntityHandler {

    Header createDocument(String documentNumber,
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

    LocalDate parseDate(String dateString) {
        if ("0000-00-00".equals(dateString)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }

    LocalTime parseTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH);
        LocalTime time = LocalTime.parse(timeString, formatter);
        return time;
    }

    void createDocumentItems() {

    }

    MaterialType createMaterialType(String materialType, String description) {
        MaterialType newMaterialType = MaterialType.create();
        newMaterialType.setLanguageCode("EN");
        newMaterialType.setMaterialType(materialType);
        newMaterialType.setMaterialTypeDescription(description);
        return newMaterialType;
    }

    Customer createCustomer(String customerNumber, 
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


}
