package com.rationalfx.clearbankgateway.service;


import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.security.SecurityHelperClearBank;
import org.glassfish.jersey.uri.UriTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClearBankAccountDeleteService {

    @Autowired
    private SecurityHelperClearBank securityHelperClearBank;

    @Autowired
    private ClearBankConfig clearBankConfig;

    public ResponseEntity<?> closeClearBankAccount(String virtualAccountId) throws UnsupportedEncodingException {

        ResponseEntity<String> responseCloseAccount = null;
        String response;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = securityHelperClearBank.getHeaderWithUniqueRequestId();
        HttpEntity<String> request = new HttpEntity<String>(null, header);
        System.out.println("requestCloseClearBankAccount======================>" + request);
        String clearBankAccountCloseURL = clearBankConfig.getClearBankInactiveAccount();
        URI expanded = URI.create(new UriTemplate(clearBankAccountCloseURL).createURI(virtualAccountId)); // this is what RestTemplate uses
        clearBankAccountCloseURL = URLDecoder.decode(expanded.toString(), "UTF-8");
        System.out.println("clearBankAccountCloseURL===============>" + clearBankAccountCloseURL);
        try {
            responseCloseAccount = restTemplate.exchange(clearBankAccountCloseURL, HttpMethod.DELETE, request, String.class);
            System.out.println("responseCloseAccount==================>" + responseCloseAccount);
            if (responseCloseAccount.getStatusCode().equals(HttpStatus.ACCEPTED) || responseCloseAccount.getStatusCode().equals(HttpStatus.OK) ||responseCloseAccount.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                System.out.println("=============================//virtualAccountClose//=============================");
                return new ResponseEntity<>(responseCloseAccount, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(responseCloseAccount.getStatusCode());
            }
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            if (responseCloseAccount == null || responseCloseAccount.getStatusCode() == null || responseCloseAccount.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                System.out.println("error in closing virtual account in clear bank");
                result.put("error", e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            } else if (responseCloseAccount == null || responseCloseAccount.getStatusCode() == null || responseCloseAccount.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                System.out.println("error in closing virtual account in clear bank");
                result.put("error", e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
            } else if (responseCloseAccount == null || responseCloseAccount.getStatusCode() == null || responseCloseAccount.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                System.out.println("error in closing virtual account in clear bank");
                result.put("error", e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            } else if (responseCloseAccount == null || responseCloseAccount.getStatusCode() == null || responseCloseAccount.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
                System.out.println("error in closing virtual account in clear bank");
                result.put("error", e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
            } else if (responseCloseAccount == null || responseCloseAccount.getStatusCode() == null || responseCloseAccount.getStatusCode().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                System.out.println("error in closing virtual account in clear bank");
                result.put("error", e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(result, HttpStatus.SERVICE_UNAVAILABLE);
            }
            else {
                throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
            }

        }
    }
}