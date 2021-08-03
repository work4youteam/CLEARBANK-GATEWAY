package com.rationalfx.clearbankgateway.controller;

import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.config.Constant;
import com.rationalfx.clearbankgateway.service.ClearBankAccountDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ClearBankAccountDeleteController {

    @Autowired
    private ClearBankConfig clearBankConfig;

    @Autowired
    private ClearBankAccountDeleteService clearBankAccountDeleteService;

    @RequestMapping(value = "/clearbank/gateway/accountdelete/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> clearBankAccountInactive(@PathVariable("id") String id, @RequestHeader Map<String, String> headersMap) throws UnsupportedEncodingException {
        String configToken=clearBankConfig.getInputsecuritytoken();
        System.out.println("configToken------------>"+configToken);
        String headerToken= headersMap.get("authorization");

        Map result= new HashMap();
        if (!configToken.equals(headerToken)){
            result.put(Constant.ERROR,Constant.INVALID_TOKEN);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
        System.out.println("clear bank id------------->" + id);
        return  clearBankAccountDeleteService.closeClearBankAccount(id);
    }
}
