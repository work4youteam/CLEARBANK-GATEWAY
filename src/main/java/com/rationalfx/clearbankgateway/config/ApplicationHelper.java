package com.rationalfx.clearbankgateway.config;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;


public class ApplicationHelper implements Constant {
    public static ResponseEntity<Map<String, Object>> getUnauthorizedInputFieldsMissing() {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ERROR, REQUIRED_INPUT_FIELDS_MISSING);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<Map<String, Object>> getUnauthorizedInputFieldsMissing(String message) {

        if (message == null) message = REQUIRED_INPUT_FIELDS_MISSING;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ERROR, message);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<Map<String, Object>> getBadGateway(String gatewayMessage) {

        if (gatewayMessage == null) gatewayMessage = BAD_GATEWAY_ERROR;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ERROR, gatewayMessage);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_GATEWAY);
    }

    public static ResponseEntity<Map<String, Object>> getUnauthorizedCustomError(String errorMessage) {

        if (errorMessage == null) errorMessage = HttpStatus.UNAUTHORIZED.toString();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ERROR, errorMessage);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<Map<String, Object>> getBadGatewayCustomError(String errorMessage) {

        if (errorMessage == null) errorMessage = HttpStatus.BAD_GATEWAY.toString();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ERROR, errorMessage);
        return new ResponseEntity<Map<String, Object>>(result, HttpStatus.BAD_GATEWAY);
    }

    /**
     * @param fullAccountNumber example GB11BARC20000746578936
     * @return example 200007 (6 digits after left 8 digits)
     */
    public static String getSortCodeFromFullAccountNumber(String fullAccountNumber) {

        String sortCode = "000000";
        if (fullAccountNumber != null && fullAccountNumber.trim().length() > 13) {
            sortCode = fullAccountNumber.substring(8, 14);
        } else {
            System.out.println("getSortCodeFromFullAccountNumber Called wrongly with --> " + fullAccountNumber);
        }
        return sortCode;
    }

}