package com.rationalfx.clearbankgateway.controller;

import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.config.Constant;
import com.rationalfx.clearbankgateway.model.RequestClearBankFundTransfer;
import com.rationalfx.clearbankgateway.service.ClearBankFundTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClearBankFundTransferController {
    @Autowired
    private ClearBankConfig clearBankConfig;


    @Autowired
    private ClearBankFundTransferService clearBankFundTransferService;

    @RequestMapping(value = "/clearbank/gateway/fundtransfer", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> clearBankFundMoment(@RequestBody RequestClearBankFundTransfer requestClearBankFundTransfer, @RequestHeader Map<String, String> headersMap) {
        String configToken=clearBankConfig.getInputsecuritytoken();
        System.out.println("configToken------------>"+configToken);
        String headerToken= headersMap.get("authorization");

        Map result= new HashMap();
        if (!configToken.equals(headerToken)){
            result.put(Constant.ERROR,Constant.INVALID_TOKEN);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
        System.out.println("clear bank requestPayload------------->"+requestClearBankFundTransfer);
        return clearBankFundTransferService.createFundTransfer(requestClearBankFundTransfer);
    }
}
