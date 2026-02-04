package com.steven.cap.zsmartforms.handlers;

import java.util.Map;

import com.sap.cds.ql.Update;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.db.entity.header.Header;
import cds.gen.db.entity.header.Header_;
import cds.gen.db.types.DocumentStatus;

public class updateHeaderStatus {

    public static void updateStatusToEmailed(String documentNumber, PersistenceService db) {
        db.run(Update.entity(Header_.CDS_NAME).data(Map.of(Header.STATUS,DocumentStatus.EMAILED)).byId(documentNumber));
    }

    public static void updateStatusToPrinted(String documentNumber, PersistenceService db) {
        db.run(Update.entity(Header_.CDS_NAME).data(Map.of(Header.STATUS,DocumentStatus.PRINTED)).byId(documentNumber));
    }

}
