package com.steven.cap.zsmartforms.handlers;

import java.util.Map;

import org.w3c.dom.Element;
import org.springframework.stereotype.Component;
import org.w3c.dom.NodeList;

import com.sap.cds.ql.Upsert;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.db.entity.header.Header;
import cds.gen.db.entity.header.Header_;
import cds.gen.mainservice.MainService_;

import com.steven.cap.zsmartforms.handlers.CreateEntityHandler;

@Component
@ServiceName(MainService_.CDS_NAME)
public class MainServiceGetDataHandler implements EventHandler {

    private final PersistenceService db;

    // Inject the PersistenceService to run queries
    public MainServiceGetDataHandler(PersistenceService db) {
        this.db = db;
    }

    @Before(event = CqnService.EVENT_READ)
    void getListReportData(CdsReadEventContext context) {
        try {
            Map<String,NodeList> dataMap = GetDataHandler.getDataFromDM4System();
            NodeList headerList = dataMap.getOrDefault("HEADER", null);
            if (headerList != null) {
                for(int i = 0; i < headerList.getLength(); i++) {
                    Element header = (Element) headerList.item(i);
                    Header newHeader = new CreateEntityHandler().createDocument(
                        getTagValue(header, "VBELN"),
                        getTagValue(header, "INCO1"),
                        getTagValue(header, "INCO2"),
                        getTagValue(header, "ROUTE"),
                        getTagValue(header, "LFDAT"),
                        getTagValue(header, "WADAT"),
                        getTagValue(header, "WAUHR"),
                        getTagValue(header, "KUNAG"),
                        getTagValue(header, "KUNNR")
                    );
                    db.run(Upsert.into(Header_.class).entry(newHeader));
                }
            }
        } catch (Exception e) {
            throw new ServiceException("Error fetching data: " + e.getMessage());
        }
    }

    private static String getTagValue(Element parent, String tagName) {
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() == 0) {
            return null;
        }
        return list.item(0).getTextContent();
    }

}
