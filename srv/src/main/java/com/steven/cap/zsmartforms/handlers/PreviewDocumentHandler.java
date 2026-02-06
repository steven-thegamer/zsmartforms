package com.steven.cap.zsmartforms.handlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sap.cds.ql.Update;
import com.sap.cds.ql.Upsert;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.db.entity.document.Document;
import cds.gen.db.entity.header.Header;
import cds.gen.db.entity.header.Header_;
import cds.gen.mainservice.LunchyDocumentDocuments_;
import cds.gen.mainservice.LunchyDocumentHeaders;
import cds.gen.mainservice.LunchyDocumentHeadersGenerateDocumentContext;
import cds.gen.mainservice.LunchyDocumentHeaders_;
import cds.gen.mainservice.MainService_;
@Component
@ServiceName(MainService_.CDS_NAME)
public class PreviewDocumentHandler implements EventHandler {

    private final PersistenceService db;

    // Inject the PersistenceService to run queries
    public PreviewDocumentHandler(PersistenceService db) {
        this.db = db;
    }

    @On(event = LunchyDocumentHeadersGenerateDocumentContext.CDS_NAME, entity = LunchyDocumentHeaders_.CDS_NAME)
    public void onPreviewDocument(LunchyDocumentHeadersGenerateDocumentContext context) {
        LunchyDocumentHeaders selectedDocument = db.run(context.getCqn()).single(LunchyDocumentHeaders.class);
        if(selectedDocument.getDocumentHeaderDocumentNumber() == null){
            String selectedDocumentNumber = selectedDocument.getDocumentNumber();
            String documentData = GetDataHandler.getSmartForms(selectedDocumentNumber);
            InputStream inputStream = new ByteArrayInputStream(documentData.getBytes(StandardCharsets.ISO_8859_1));
            Document newlyCreatedDocument = CreateEntityHandler.createDocument(selectedDocumentNumber, inputStream);
            db.run(Upsert.into(LunchyDocumentDocuments_.CDS_NAME)
                .entry(newlyCreatedDocument));
            db.run(Update.entity(Header_.CDS_NAME)
                .data(Map.of(Header.DOCUMENT_HEADER_DOCUMENT_NUMBER, selectedDocumentNumber))
                .byId(selectedDocumentNumber));
        }
        context.setCompleted();
    }
}