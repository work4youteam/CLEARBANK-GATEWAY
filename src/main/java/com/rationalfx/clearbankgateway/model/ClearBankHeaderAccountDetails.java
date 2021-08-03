package com.rationalfx.clearbankgateway.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClearBankHeaderAccountDetails {
    public Account account;

    public List<HalLink> halLinks;

    public Account getAccount() {

        return account;
    }

    public void setAccount(Account account) {

        this.account = account;
    }

    public List<HalLink> getHalLinks() {

        return halLinks;
    }

    public void setHalLinks(List<HalLink> halLinks) {

        this.halLinks = halLinks;
    }

    @Override
    public String toString() {

        return "ClearBankHeaderAccountDetails{" +
                "account=" + account +
                ", halLinks=" + halLinks +
                '}';
    }
}
