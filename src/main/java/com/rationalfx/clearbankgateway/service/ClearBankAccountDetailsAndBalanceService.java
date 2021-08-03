package com.rationalfx.clearbankgateway.service;

import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.model.ClearBankHeaderAccountDetails;
import com.rationalfx.clearbankgateway.security.SecurityHelperClearBank;
import org.glassfish.jersey.uri.UriTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


@Service
public class ClearBankAccountDetailsAndBalanceService {

    @Autowired
    private SecurityHelperClearBank securityHelperClearBank;

    @Autowired
    private ClearBankConfig clearBankConfig;

    public ResponseEntity<?> getAccountDetails(String transaction_id) throws Exception {

        ResponseEntity<ClearBankHeaderAccountDetails> accountDetailsResponse = null;
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(null, securityHelperClearBank.getHeaderWithBearerToken());
        System.out.println("requestClearBankGetAccountDetails======================>" + request);
        String strURLGetLedgerDetailsIn = clearBankConfig.getClearBankHeaderAccountDetailsURL();
        URI expanded = URI.create(new UriTemplate(strURLGetLedgerDetailsIn).createURI(transaction_id)); // this is what RestTemplate uses
        strURLGetLedgerDetailsIn = URLDecoder.decode(expanded.toString(), "UTF-8");
        System.out.println("url===============>" + strURLGetLedgerDetailsIn);
        try {
            accountDetailsResponse = restTemplate.exchange(strURLGetLedgerDetailsIn, HttpMethod.GET, request, ClearBankHeaderAccountDetails.class);
            if (accountDetailsResponse.getStatusCode().equals(HttpStatus.ACCEPTED) || accountDetailsResponse.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println("Response-responseClearBankAccountDetails===========>" + accountDetailsResponse.getBody());
                return accountDetailsResponse;
            } else {
                return new ResponseEntity<>(accountDetailsResponse.getStatusCode());
            }
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            if (accountDetailsResponse == null || accountDetailsResponse.getStatusCode() == null || accountDetailsResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                result.put("error", e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
            } else {
                throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
            }
        }
    }
}