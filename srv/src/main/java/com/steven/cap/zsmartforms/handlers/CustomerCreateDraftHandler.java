package com.steven.cap.zsmartforms.handlers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.services.draft.DraftService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.customerservice.CustomerService_;
import cds.gen.customerservice.LunchyDocumentCustomersCreateDraftContext;
import cds.gen.customerservice.LunchyDocumentCustomers_;
import cds.gen.db.entity.customer.Customer;
import cds.gen.db.entity.customer.Customer_;

@Component
@ServiceName(CustomerService_.CDS_NAME)
public class CustomerCreateDraftHandler implements EventHandler {

    @Qualifier(CustomerService_.CDS_NAME)
    private PersistenceService db;
    private DraftService customerService;

    // Inject the PersistenceService to run queries
    public CustomerCreateDraftHandler(PersistenceService db, 
        @Qualifier(CustomerService_.CDS_NAME) DraftService customerService) {
        this.db = db;
        this.customerService = customerService;
    }

    private final String initialCode = "CAP";

    @On(entity = LunchyDocumentCustomers_.CDS_NAME, event = LunchyDocumentCustomersCreateDraftContext.CDS_NAME)
    Customer onCreateDraftCustomer(LunchyDocumentCustomersCreateDraftContext context) {
        Customer newCustomer = Customer.create();
        Integer totalDocument = db.run(
            Select.from(Customer_.CDS_NAME)
        ).listOf(Customer.class).size();
        String newDocumentNumber = String.format("%s%06d", initialCode, totalDocument + 1);
        newCustomer.setCustomerNumber(newDocumentNumber);
        return customerService.newDraft(Insert.into(LunchyDocumentCustomers_.CDS_NAME).entry(newCustomer)).single(Customer.class);
    }

}
