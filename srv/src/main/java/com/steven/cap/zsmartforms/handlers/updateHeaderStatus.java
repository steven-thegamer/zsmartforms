package com.steven.cap.zsmartforms.handlers;

import org.springframework.stereotype.Component;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.db.entity.header.Header;
import cds.gen.db.entity.header.Header_;
import cds.gen.db.types.DocumentStatus;

public class updateHeaderStatus {

    public static void updateStatusToEmailed(String documentNumber, PersistenceService db) {
        Header updateThisDocument = db.run(Select.from(Header_.CDS_NAME).where(
            t -> t.get(Header.DOCUMENT_NUMBER).eq(documentNumber)
        )).single(Header.class);

        updateThisDocument.setStatus(DocumentStatus.EMAILED);
        db.run(Update.entity(Header_.CDS_NAME).data(updateThisDocument));
    }

    public static void updateStatusToPrinted(String documentNumber, PersistenceService db) {
        Header updateThisDocument = db.run(Select.from(Header_.CDS_NAME).where(
            t -> t.get(Header.DOCUMENT_NUMBER).eq(documentNumber)
        )).single(Header.class);

        updateThisDocument.setStatus(DocumentStatus.PRINTED);
        db.run(Update.entity(Header_.CDS_NAME).data(updateThisDocument));
    }

}
