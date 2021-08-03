package com.rationalfx.clearbankgateway.service;

import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.config.Constant;
import com.rationalfx.clearbankgateway.model.RequestClearBankFundTransfer;
import com.rationalfx.clearbankgateway.security.ClearBankDigitalSignature;
import com.rationalfx.clearbankgateway.security.SecurityHelperClearBank;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Component
public class ClearBankFundTransferService {

    @Autowired
    private ClearBankRequestFundTransfer clearBankRequestFundTransfer;

    @Autowired
    private ClearBankDigitalSignature clearBankDigitalSignature;

    @Autowired
    private SecurityHelperClearBank securityHelperClearBank;

    @Autowired
    private ClearBankConfig clearBankConfig;


    public ResponseEntity<Map<String, Object>> createFundTransfer(RequestClearBankFundTransfer requestClearBankFundTransfer) {
        ResponseEntity<String> responseEntityStr = null;
        try {
            String bankPostURL;
            System.out.println("***************PaymentTransferBody Request Body :\n\n" + clearBankRequestFundTransfer.getPaymentTransferBody(requestClearBankFundTransfer));
            String digitalSignature = clearBankDigitalSignature.getDigitalSignatureFromService(clearBankRequestFundTransfer.getPaymentTransferBody(requestClearBankFundTransfer));
            System.out.println("DigitalSignature from service is ::" + digitalSignature);
            if (digitalSignature == null || digitalSignature.equals("")) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("Error", "Error in Getting Digital Signatue");
                return new ResponseEntity<>(result, HttpStatus.BAD_GATEWAY);
            }
            HttpHeaders header = securityHelperClearBank.getHeaderWithBearerTokenAndUniqueHeaderAndDigitalSignature(digitalSignature);
            HttpEntity<String> request = new HttpEntity<>(clearBankRequestFundTransfer.getPaymentTransferBody(requestClearBankFundTransfer), header);
            RestTemplate restTemplate = new RestTemplate();
            if (Constant.AMOUNT_LIMIT.compareTo(requestClearBankFundTransfer.getAmount()) > -1) {
                bankPostURL = clearBankConfig.getClearBankFundURL() + "FPS";
                System.out.println("Payment mode less then 250000");
            } else {
                bankPostURL = clearBankConfig.getClearBankFundURL() + "CHAPS";
                System.out.println("Payment mode greater then 250000");
            }
            responseEntityStr = restTemplate.postForEntity(bankPostURL, request, String.class);
            // Response code:202 ACCEPTED
            System.out.println("Response code:" + responseEntityStr.getStatusCode() + " \n CLEARBANK TO BARCLAYSFUND TRANSFER RESPONSE IS -------------------->" + responseEntityStr + "\n\n");
            if (responseEntityStr.getStatusCode().equals(HttpStatus.ACCEPTED) || responseEntityStr.getStatusCode().equals(HttpStatus.OK)) {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("result", "OK");
                return new ResponseEntity<>(result, responseEntityStr.getStatusCode());
            } else {
                throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            Map<String, Object> result = new HashMap<>();
            if (responseEntityStr == null || responseEntityStr.getStatusCode() == null || responseEntityStr.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                result.put("Error", "Error in create funding to barclay");
                System.out.println(result);
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            } else {
                throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY);
            }
        }
    }
}