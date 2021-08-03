package com.rationalfx.clearbankgateway.service;


import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.config.Constant;
import com.rationalfx.clearbankgateway.model.VirtualAccountRequest;
import com.rationalfx.clearbankgateway.security.ClearBankDigitalSignature;
import com.rationalfx.clearbankgateway.security.SecurityHelperClearBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClearBankCreateVirtualAccountService extends SecurityHelperClearBank {

    @Autowired
    private ClearBankConfig clearBankConfig;

    @Autowired
    private ClearBankDigitalSignature clearBankDigitalSignature;

    public ResponseEntity<Map<String, Object>> createVirtualAccount(@RequestBody VirtualAccountRequest virtualAccountRequest) throws Exception {

        System.out.println("\n\n\n Service Hit createVirtualAccount : VirtualAccountRequest:::" + virtualAccountRequest);
        String virtualAccountBody = "{\"virtualAccounts\":[{\"ownerName\":\"" + virtualAccountRequest.getOwnerName() + "\",\"accountIdentifier\":{\"iban\":\"\",\"bban\":\"\",\"proprietaryIdentifier\":\"" + virtualAccountRequest.getAccountNumber() + "\"}}]}";
        System.out.println("createVirtualAccount Request Body :" + virtualAccountBody);
        String digitalSignature = clearBankDigitalSignature.getDigitalSignatureFromService(virtualAccountBody);
        System.out.println("DigitalSignature from service is ::" + digitalSignature);
        if (digitalSignature == null || digitalSignature.equals("")) {
            Map<String, Object> result = new HashMap<>();
            result.put("Error", "Error in Getting Digital Signatue");
            return new ResponseEntity<>(result, HttpStatus.BAD_GATEWAY);
        }

        String hexMillis = Long.toHexString(Instant.now().toEpochMilli());
        String uniqueHeader = Constant.UNIQUE_KEY_PREFIX + hexMillis + "-" + getRandomNumber(12);
        HttpHeaders header = getHeaderForPost(digitalSignature, uniqueHeader);
        HttpEntity<String> request = new HttpEntity<String>(virtualAccountBody, header);
        RestTemplate restTemplate = new RestTemplate();
        String bankPostURL = clearBankConfig.getUrlVirtualAccountCreate().replace("$IDENTIFIER$", clearBankConfig.getParentcollection());

        try {
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(bankPostURL, request, String.class);
            System.out.println("Response code:" + responseEntityStr.getStatusCode() + " and Response is -->" + responseEntityStr);

            if (responseEntityStr.getStatusCode().equals(HttpStatus.ACCEPTED) || responseEntityStr.getStatusCode().equals(HttpStatus.OK)) {
                Map<String, Object> result = new HashMap<>();
                result.put("result", "OK");
                result.put("ownername", virtualAccountRequest.getOwnerName());
                result.put("accountnumber", virtualAccountRequest.getAccountNumber());
                result.put("uniqueHeader", uniqueHeader);
                return new ResponseEntity<>(result, responseEntityStr.getStatusCode());
            } else {
                throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
            }
        } catch (HttpClientErrorException e) {
            System.out.println("Error in Posting Virtual Account Request to Clear Bank:" + e.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("Error", "Error in Posting Virtual Account Request to Clear Bank");
           return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

}
