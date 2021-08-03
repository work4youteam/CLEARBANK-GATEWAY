package com.rationalfx.clearbankgateway.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClearBankDigitalSignature {

    @Autowired
    private ClearBankConfig clearBankConfig;

    public String getDigitalSignatureFromService(String jsonBody) {

        String token = "";
        ResponseEntity<String> responseEntityStr=null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonBody, header);
        try {
            responseEntityStr = restTemplate.postForEntity(clearBankConfig.getDigitalsignatureapi(), request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actualObj = mapper.readValue(responseEntityStr.getBody(), JsonNode.class);
            token = actualObj.get("DigitalSignature").textValue();
            if (responseEntityStr.getStatusCode().equals(HttpStatus.ACCEPTED) || responseEntityStr.getStatusCode().equals(HttpStatus.OK)) {
                return token;
            }else {
                throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            if (responseEntityStr == null || responseEntityStr.getStatusCode() == null || responseEntityStr.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                result.put("Error", "Error in getting digital signature from Clear Bank");
                System.out.println(result);
                return e.getMessage();
            }
        }
        return String.valueOf(responseEntityStr);
    }
}
