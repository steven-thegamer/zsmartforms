package com.steven.cap.zsmartforms.handlers;

import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sap.cds.services.ServiceException;

public class GetDataHandler {

    @SuppressWarnings("null")
    public static Map<String,NodeList> getDataFromDM4System() throws ParserConfigurationException, SAXException, IOException {
        Map<String,NodeList> resultMap = new java.util.HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {
            String url = "http://handhana01.hand-china.com:8050/sap/z47818_cap_sfrm?sap-client=300";
            
            // Add Basic Authentication
            String username = "47818";
            String password = "Handhand@123";
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Failed to fetch data from external service: " + e.getMessage());
        }
        
        if (response == null || !response.getStatusCode().is2xxSuccessful()) {
            throw new ServiceException("Invalid response from external service");
        }

        String resultOutput = response.getBody();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(resultOutput));
        Document document = builder.parse(inputSource);
        document.getDocumentElement().normalize();

        // Read HEADER / LIKP
        NodeList likpList = document.getElementsByTagName("LIKP");
        if (likpList.getLength() > 0) {
            resultMap.put("HEADER", likpList);
            /* 
            for(int i = 0; i < likpList.getLength(); i++) {
                Element likp = (Element) likpList.item(i);
                System.out.println("VBELN  : " + getTagValue(likp, "VBELN"));
                System.out.println("INCO1  : " + getTagValue(likp, "INCO1"));
                System.out.println("INCO2  : " + getTagValue(likp, "INCO2"));
                System.out.println("ROUTE  : " + getTagValue(likp, "ROUTE"));
                System.out.println("LFDAT  : " + getTagValue(likp, "LFDAT"));
                System.out.println("WADAT  : " + getTagValue(likp, "WADAT"));
                System.out.println("WAUHR  : " + getTagValue(likp, "WAUHR"));
                System.out.println("KUNAG  : " + getTagValue(likp, "KUNAG"));
                System.out.println("KUNNR  : " + getTagValue(likp, "KUNNR"));
                System.out.println("-----");
            }
            */
        }
    
        // Read ITEM / LIPS
        NodeList lipsList = document.getElementsByTagName("LIPS");
        if (lipsList.getLength() > 0) {
            resultMap.put("ITEMS", lipsList);
            /*
            for(int i = 0; i < lipsList.getLength(); i++) {
                Element lips = (Element) lipsList.item(i);
                System.out.println("VBELN  : " + getTagValue(lips, "VBELN"));
                System.out.println("POSNR  : " + getTagValue(lips, "POSNR"));
                System.out.println("MATNR  : " + getTagValue(lips, "MATNR"));
                System.out.println("MTART  : " + getTagValue(lips, "MTART"));
                System.out.println("ARKTX  : " + getTagValue(lips, "ARKTX"));
                System.out.println("LFIMG  : " + getTagValue(lips, "LFIMG"));
                System.out.println("MEINS  : " + getTagValue(lips, "MEINS"));
                System.out.println("LGMNG  : " + getTagValue(lips, "LGMNG"));
                System.out.println("-----");
            }
            */
        }

        // Read Material Type / T134T
        NodeList t134tList = document.getElementsByTagName("T134T");
        if (t134tList.getLength() > 0) {
            resultMap.put("MATERIAL_TYPES", t134tList);
            /* 
            for(int i = 0; i < t134tList.getLength(); i++) {
                Element t134t = (Element) t134tList.item(i);
                System.out.println("MTART  : " + getTagValue(t134t, "MTART"));
                System.out.println("MTBEZ  : " + getTagValue(t134t, "MTBEZ"));
                System.out.println("-----");
            }
            */
        }

        // Read Customer / KNA1
        NodeList kna1List = document.getElementsByTagName("KNA1");
        if (kna1List.getLength() > 0) {
            resultMap.put("CUSTOMERS", kna1List);
            /*
            for(int i = 0; i < kna1List.getLength(); i++) {
                Element kna1 = (Element) kna1List.item(i);
                System.out.println("KUNNR  : " + getTagValue(kna1, "KUNNR"));
                System.out.println("NAME1  : " + getTagValue(kna1, "NAME1"));
                System.out.println("LAND1  : " + getTagValue(kna1, "LAND1"));
                System.out.println("TELF1  : " + getTagValue(kna1, "TELF1"));
                System.out.println("PSTLZ  : " + getTagValue(kna1, "PSTLZ"));
                System.out.println("STRAS  : " + getTagValue(kna1, "STRAS"));
                System.out.println("-----");
            }
            */
        }

        return resultMap;
    }
    
}
