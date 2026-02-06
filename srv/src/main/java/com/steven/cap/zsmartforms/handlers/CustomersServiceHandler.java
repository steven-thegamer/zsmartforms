package com.steven.cap.zsmartforms.handlers;

import org.springframework.stereotype.Component;

import com.sap.cds.ql.Insert;
import com.sap.cds.services.draft.DraftService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.customersservice.Customers;
import cds.gen.customersservice.CustomersCreateDraftContext;
import cds.gen.customersservice.CustomersService_;
import cds.gen.db.index.Customers_;

@Component
@ServiceName(CustomersService_.CDS_NAME)
public class CustomersServiceHandler implements EventHandler {
    
    private final PersistenceService db;
    private final DraftService customersService;

    /**
     * Constructor to inject the PersistenceService for database operations.
     * @param db PersistenceService instance for running queries
     */
    public CustomersServiceHandler(PersistenceService db, DraftService customersService) {
        this.db = db;
        this.customersService = customersService;
    }
    @On(entity = CustomersService_.CDS_NAME)
    public void createDraft(CustomersCreateDraftContext context) {
        Customers draftCustomer = Customers.create();
        draftCustomer.setCustomerNumber(context.getCustomerNo());
        context.setResult(customersService.newDraft(
            Insert.into(Customers_.CDS_NAME)
            .entry(draftCustomer)).single(Customers.class));
    }
}
