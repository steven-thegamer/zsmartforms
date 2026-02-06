package com.steven.cap.zsmartforms.handlers;

import org.springframework.stereotype.Component;

import com.sap.cds.ql.Insert;
import com.sap.cds.ql.Select;
import com.sap.cds.services.draft.DraftService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.customersservice.Customers;
import cds.gen.customersservice.CustomersCreateDraftContext;
import cds.gen.customersservice.CustomersService_;
import cds.gen.customersservice.Customers_;

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

    private final String prefixCustomerNumber = "CUST";

    @On(entity = Customers_.CDS_NAME, event = CustomersCreateDraftContext.CDS_NAME)
    public void createDraft(CustomersCreateDraftContext context) {
        Integer existingCount = db.run(Select.from(Customers_.CDS_NAME))
        .listOf(Customers.class).size();
        Customers draftCustomer = Customers.create();
        draftCustomer.setCustomerNumber(prefixCustomerNumber + String.format("%06d", existingCount + 1));
        context.setResult(customersService.newDraft(
            Insert.into(Customers_.CDS_NAME)
            .entry(draftCustomer))
            .single(Customers.class));
        context.setCompleted();
    }
}
