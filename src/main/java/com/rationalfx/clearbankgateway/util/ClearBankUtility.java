package com.rationalfx.clearbankgateway.util;

import com.rationalfx.clearbankgateway.config.Constant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public interface ClearBankUtility {
    public static HttpHeaders getClearBankHeader(String clearBankSecurityKey) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(Constant.AUTHORIZATION, clearBankSecurityKey);
        return headers;
    }
}
