package com.rationalfx.clearbankgateway.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rationalfx.clearbankgateway.config.ApplicationHelper;
import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.config.Constant;
import com.rationalfx.clearbankgateway.model.VirtualAccountRequest;
import com.rationalfx.clearbankgateway.service.ClearBankCreateVirtualAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClearBankCreateVirtualAccountController {


    @Autowired
    private ClearBankConfig clearBankConfig;

    @Autowired
    private ClearBankCreateVirtualAccountService clearBankCreateVirtualAccountService;

    @PostMapping(value = "/clearbankgateway/createvirtualaccount", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Map<String, Object>> createVirtualAccount(@RequestBody JsonNode request, @RequestHeader Map<String, String> headersMap) {


        String configToken = clearBankConfig.getInputsecuritytoken();
        String headerToken = headersMap.get(Constant.AUTHORIZATION);
        if (!configToken.equals(headerToken)) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put(Constant.ERROR,Constant.AUTH_BEARER_TOKEN_MISSING);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        ResponseEntity<Map<String, Object>> responseEntity = null;
        ObjectMapper objectMapper = new ObjectMapper();
        VirtualAccountRequest toValue = objectMapper.convertValue(request, VirtualAccountRequest.class);
        if (!toValue.isValid()) {
            return ApplicationHelper.getUnauthorizedInputFieldsMissing(toValue.getErrorMessage());
        }
        try {
            responseEntity = clearBankCreateVirtualAccountService.createVirtualAccount(toValue);
        } catch (Exception e) {
            e.printStackTrace();
            return ApplicationHelper.getBadGateway(Constant.VIRTUAL_ACCOUNT_CREATION_FAILED);
        }
        return responseEntity;
    }
}
