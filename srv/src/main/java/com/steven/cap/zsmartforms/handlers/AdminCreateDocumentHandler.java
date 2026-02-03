package com.steven.cap.zsmartforms.handlers;

import org.springframework.stereotype.Component;

import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.adminservice.AdminService_;

@Component
@ServiceName(AdminService_.CDS_NAME)
public class AdminCreateDocumentHandler implements EventHandler {
    
    private final PersistenceService db;

    // Inject the PersistenceService to run queries
    public AdminCreateDocumentHandler(PersistenceService db) {
        this.db = db;
    }


}
