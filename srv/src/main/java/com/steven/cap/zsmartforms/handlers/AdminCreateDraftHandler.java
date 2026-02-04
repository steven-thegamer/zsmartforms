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
import cds.gen.adminservice.LunchyDocumentHeadersCreateDraftContext;
import cds.gen.adminservice.LunchyDocumentHeaders_;
import cds.gen.db.entity.header.Header;
import cds.gen.db.entity.header.Header_;

@Component
@ServiceName(AdminService_.CDS_NAME)
public class AdminCreateDraftHandler implements EventHandler {

    @Qualifier(AdminService_.CDS_NAME)
    private PersistenceService db;
    private DraftService adminService;

    // Inject the PersistenceService to run queries
    public AdminCreateDraftHandler(PersistenceService db, @Qualifier("AdminService") DraftService adminService) {
        this.db = db;
        this.adminService = adminService;
    }

    private final String initialCode = "CAP";

    @On(entity = LunchyDocumentHeaders_.CDS_NAME, event = LunchyDocumentHeadersCreateDraftContext.CDS_NAME)
    Header onCreateDraftHeader(LunchyDocumentHeadersCreateDraftContext context) {
        Header newHeader = Header.create();
        Integer totalDocument = db.run(
            Select.from(Header_.CDS_NAME)
        ).listOf(Header.class).size();
        String newDocumentNumber = String.format("%s%06d", initialCode, totalDocument + 1);
        newHeader.setDocumentNumber(newDocumentNumber);
        return adminService.newDraft(Insert.into(LunchyDocumentHeaders_.CDS_NAME).entry(newHeader)).single(Header.class);
    }
}
