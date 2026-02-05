package com.steven.cap.zsmartforms.handlers;

import org.springframework.stereotype.Component;

import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.mainservice.MainService_;
@Component
@ServiceName(MainService_.CDS_NAME)
public class DownloadDocumentHandler implements EventHandler {

    private final PersistenceService db;

    // Inject the PersistenceService to run queries
    public DownloadDocumentHandler(PersistenceService db) {
        this.db = db;
    }

    // @Before(event = LunchyDocumentHeadersDownloadDocumentContext.CDS_NAME, entity = LunchyDocumentHeaders_.CDS_NAME)
    // public void beforeDownloadDocument(LunchyDocumentHeadersDownloadDocumentContext context) {
    //     LunchyDocumentHeaders selectedDocument = db.run(context.getCqn()).single(LunchyDocumentHeaders.class);
    //     if(selectedDocument.getDocumentNumber().startsWith("CAP")){
    //         throw new ServiceException("This is a custom document made in SAP CAP! This doesn't exist in the SAP System!");
    //     }
    // }

    // @On(event = LunchyDocumentHeadersDownloadDocumentContext.CDS_NAME, entity = LunchyDocumentHeaders_.CDS_NAME)
    // public void onDownloadDocument(LunchyDocumentHeadersDownloadDocumentContext context) {
    //     LunchyDocumentHeaders selectedDocument = db.run(context.getCqn()).single(LunchyDocumentHeaders.class);
    //     String selectedDocumentNumber = selectedDocument.getDocumentNumber();
    //     System.out.println("On downloading document for document number: " + selectedDocumentNumber);
    //     String documentData = GetDataHandler.getSmartForms(selectedDocumentNumber);
    //     InputStream inputStream = new ByteArrayInputStream(documentData.getBytes(StandardCharsets.ISO_8859_1));
    //     File destinationFile = new File("C:\\Users\\IT\\Downloads\\" + selectedDocument.getDocumentNumber() + ".pdf");
    //     try (FileOutputStream fileOutputStream = new FileOutputStream(destinationFile)) {
    //         byte[] buffer = new byte[1024];
    //         int bytesRead;
    //         BufferedInputStream bis = new BufferedInputStream(inputStream);
    //         while ((bytesRead = bis.read(buffer)) != -1) fileOutputStream.write(buffer, 0, bytesRead);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
        
    //     db.run(Upsert.into(LunchyDocumentDocuments_.CDS_NAME)
    //     .entry(CreateEntityHandler.createDocument(selectedDocumentNumber, inputStream)));
    //     context.setCompleted();
    // }

    // @After(event = LunchyDocumentHeadersDownloadDocumentContext.CDS_NAME, entity = LunchyDocumentHeaders_.CDS_NAME)
    // public void afterDownloadDocument(LunchyDocumentHeadersDownloadDocumentContext context) {
    //     Result result = db.run(context.getCqn());
    //     result.forEach(t -> {
    //         String documentNumber = (String) t.get("documentNumber");
    //         UpdateHeaderStatusHandler.updateStatusToPrinted(documentNumber, db);
    //     });
    // }


    
}
