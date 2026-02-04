package com.steven.cap.zsmartforms.handlers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.sap.cds.Result;
import com.sap.cds.ql.Upsert;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.mainservice.LunchyDocumentHeadersDownloadDocumentContext;
import cds.gen.mainservice.LunchyDocumentHeaders_;
import cds.gen.mainservice.MainService_;
import cds.gen.mainservice.LunchyDocumentDocuments_;
import cds.gen.mainservice.LunchyDocumentHeaders;
@Component
@ServiceName(MainService_.CDS_NAME)
public class DownloadDocumentHandler implements EventHandler {

    private final PersistenceService db;

    // Inject the PersistenceService to run queries
    public DownloadDocumentHandler(PersistenceService db) {
        this.db = db;
    }

    @Before(event = LunchyDocumentHeadersDownloadDocumentContext.CDS_NAME, entity = LunchyDocumentHeaders_.CDS_NAME)
    public void beforeDownloadDocument(LunchyDocumentHeadersDownloadDocumentContext context) {
        // Example logic for handling the download document event
        System.out.println("Before downloading document");
    }

    @On(event = LunchyDocumentHeadersDownloadDocumentContext.CDS_NAME, entity = LunchyDocumentHeaders_.CDS_NAME)
    public void onDownloadDocument(LunchyDocumentHeadersDownloadDocumentContext context) {
        // Example logic for handling the download document event
        
        LunchyDocumentHeaders selectedDocument = db.run(context.getCqn()).single(LunchyDocumentHeaders.class);
        String selectedDocumentNumber = selectedDocument.getDocumentNumber();
        System.out.println("On downloading document for document number: " + selectedDocumentNumber);
        String documentData = GetDataHandler.getSmartForms(selectedDocumentNumber);
        InputStream inputStream = new ByteArrayInputStream(documentData.getBytes(StandardCharsets.ISO_8859_1));
        File destinationFile = new File("C:\\Users\\IT\\Downloads\\" + selectedDocument.getDocumentNumber() + ".pdf");
        try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            while ((bytesRead = bis.read(buffer)) != -1) fileOutputStream.write(buffer, 0, bytesRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        db.run(Upsert.into(LunchyDocumentDocuments_.CDS_NAME)
        .entry(CreateEntityHandler.createDocument(selectedDocumentNumber, inputStream)));
        context.setCompleted();

    }

    @After(event = LunchyDocumentHeadersDownloadDocumentContext.CDS_NAME, entity = LunchyDocumentHeaders_.CDS_NAME)
    public void afterDownloadDocument(LunchyDocumentHeadersDownloadDocumentContext context) {
        // Example logic for handling the download document event
        System.out.println("After downloading document for context: " + context.getCqn());

        Result result = db.run(context.getCqn());

        result.forEach(t -> {
            String documentNumber = (String) t.get("documentNumber");
            System.out.println("Updating status to PRINTED for document number: " + documentNumber);
            updateHeaderStatus.updateStatusToPrinted(documentNumber, db);
        });

        //updateHeaderStatus.updateStatusToPrinted(selectedDocumentNumber, db);
    }


    
}
