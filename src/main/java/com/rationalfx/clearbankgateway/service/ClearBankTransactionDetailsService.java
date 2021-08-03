package com.rationalfx.clearbankgateway.service;

import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.model.ResponseClearBank;
import com.rationalfx.clearbankgateway.util.ClearBankUtility;
import org.glassfish.jersey.uri.UriTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


@Service
public class ClearBankTransactionDetailsService {

    @Autowired
    private ClearBankConfig clearBankConfig;

    public ResponseEntity getTransactionDetails(String transaction_id) throws Exception {

        ResponseEntity<ResponseClearBank> responseClearBankResponseEntity = null;
        HttpEntity<String> requestHttp = new HttpEntity<>(ClearBankUtility.getClearBankHeader(clearBankConfig.getTokenBearerPostClearBank()));
        System.out.println("Request get transaction details=====================>" + requestHttp);
        RestTemplate restTemplate = new RestTemplate();
        String clearBankURL = clearBankConfig.getClearbankgettransactionurl();
        URI expanded = URI.create(new UriTemplate(clearBankURL).createURI(transaction_id)); // this is what RestTemplate uses
        clearBankURL = URLDecoder.decode(expanded.toString(), "UTF-8");
        System.out.println("url===============>"+clearBankURL);

        try {
            responseClearBankResponseEntity = restTemplate.exchange(clearBankURL, HttpMethod.GET, requestHttp, ResponseClearBank.class);
            if (responseClearBankResponseEntity.getStatusCode().equals(HttpStatus.ACCEPTED) || responseClearBankResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println("Response-responseClearBankResponseEntity===========>" + responseClearBankResponseEntity.getBody());
                return responseClearBankResponseEntity;
            } else {
                return new ResponseEntity<>(responseClearBankResponseEntity.getStatusCode());
            }
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            if (responseClearBankResponseEntity == null || responseClearBankResponseEntity.getStatusCode() == null || responseClearBankResponseEntity.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                result.put("error", e.getMessage());
                e.printStackTrace();
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
                } else {
                    throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
                }
        }
    }
}