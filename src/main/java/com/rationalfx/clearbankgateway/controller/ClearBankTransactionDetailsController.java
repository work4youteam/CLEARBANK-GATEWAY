package com.rationalfx.clearbankgateway.controller;

import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.config.Constant;
import com.rationalfx.clearbankgateway.service.ClearBankTransactionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClearBankTransactionDetailsController {


    @Autowired
    private ClearBankConfig clearBankConfig;


    @Autowired
    private ClearBankTransactionDetailsService clearBankTransactionDetailsService;

    @RequestMapping(value = "/clearbankgateway/transaction/{transaction_id}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> clearBankAccountDetails(@PathVariable("transaction_id") String transaction_id, @RequestHeader Map<String, String> headersMap) throws Exception {
        String configToken=clearBankConfig.getInputsecuritytoken();
        System.out.println("configToken------------>"+configToken);
        String headerToken= headersMap.get("authorization");

        Map result= new HashMap();
        if (!configToken.equals(headerToken)){
            result.put(Constant.ERROR,Constant.INVALID_TOKEN);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
        System.out.println("clear bank id------------->"+transaction_id);
        return clearBankTransactionDetailsService.getTransactionDetails(transaction_id);
    }
}
