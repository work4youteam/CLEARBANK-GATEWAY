package com.rationalfx.clearbankgateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class VirtualAccountRequest {
    @NotNull
    String ownerName;

    @NotNull
    String accountNumber;

    @NotNull
    String accountType;

    public boolean isValid() {

        return this.accountNumber != null && this.accountType != null && this.ownerName != null;
    }

    public String getOwnerName() {

        return ownerName;
    }

    public void setOwnerName(String ownerName) {

        this.ownerName = ownerName;
    }

    public String getAccountNumber() {

        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {

        this.accountNumber = accountNumber;
    }

    public String getAccountType() {

        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getErrorMessage() {

        String message = "";
        if (!StringUtils.hasText(this.accountNumber)) {
            message = message + "Missing accountNumber ";
        }

        if (!StringUtils.hasText(this.accountType)) {
            message = message + "Missing accountType ";
        }

        if (!StringUtils.hasText(this.ownerName)) {
            message = message + "Missing ownerName ";
        }
        return message;
    }

}
