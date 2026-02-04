package com.steven.cap.zsmartforms.handlers;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
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

    private static final String URL = "http://handhana01.hand-china.com:8050/sap/z47818_cap_sfrm?sap-client=300";
    private static final String username = "47818";
    private static final String password = "Handhand@123";

    @SuppressWarnings("null")
    public static Map<String,NodeList> getDataFromDM4System() throws ParserConfigurationException, SAXException, IOException {
        Map<String,NodeList> resultMap = new java.util.HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {            
            // Add Basic Authentication
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
            
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
        }
    
        // Read ITEM / LIPS
        NodeList lipsList = document.getElementsByTagName("LIPS");
        if (lipsList.getLength() > 0) {
            resultMap.put("ITEMS", lipsList);
        }

        // Read Material Type / T134T
        NodeList t134tList = document.getElementsByTagName("T134T");
        if (t134tList.getLength() > 0) {
            resultMap.put("MATERIAL_TYPES", t134tList);
        }

        // Read Customer / KNA1
        NodeList kna1List = document.getElementsByTagName("KNA1");
        if (kna1List.getLength() > 0) {
            resultMap.put("CUSTOMERS", kna1List);
        }

        return resultMap;
    }
   
    public static String getSmartForms(String documentNumber) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {            
            // Add Basic Authentication
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            String jsonInputString = "{\"VBELN\": \"" + documentNumber + "\"}";
            
            HttpEntity<String> entity = new HttpEntity<>(jsonInputString, headers);
            response = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Failed to fetch data from external service: " + e.getMessage());
        }
        
        if (response == null || !response.getStatusCode().is2xxSuccessful()) {
            throw new ServiceException("Invalid response from external service");
        }

        byte[] responseBytes = response.getBody().getBytes(StandardCharsets.UTF_8);

        return new String(responseBytes);
    }
}
