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

import cds.gen.adminservice.AdminService_;
import cds.gen.adminservice.Headers;
import cds.gen.adminservice.Headers_;
import cds.gen.adminservice.HeadersCreateDraftContext;

@Component
@ServiceName(AdminService_.CDS_NAME)
public class AdminServiceHandler implements EventHandler {

    private final PersistenceService db;
    private final DraftService adminService;

    /**
     * Constructor to inject the PersistenceService for database operations.
     * @param db PersistenceService instance for running queries
     */
    public AdminServiceHandler(PersistenceService db, @Qualifier(AdminService_.CDS_NAME) DraftService adminService) {
        this.db = db;
        this.adminService = adminService;
    }

    private final String prefixHeaderNumber = "HDR";

    @On(entity = Headers_.CDS_NAME, event = HeadersCreateDraftContext.CDS_NAME)
    public void createDraft(HeadersCreateDraftContext context) {
        Integer existingCount = db.run(Select.from(Headers_.CDS_NAME))
        .listOf(Headers.class).size();
        Headers draftHeader = Headers.create();
        draftHeader.setDocumentNumber(prefixHeaderNumber + String.format("%07d", existingCount + 1));
        context.setResult(adminService.newDraft(
            Insert.into(Headers_.CDS_NAME)
            .entry(draftHeader))
            .single(Headers.class));
        context.setCompleted();
    }

}
