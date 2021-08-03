package com.rationalfx.clearbankgateway.model;

import java.util.List;

public class ResponseClearBank {
   private List<Account> accounts;
   private List<HalLink> halLinks;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<HalLink> getHalLinks() {
        return halLinks;
    }

    public void setHalLinks(List<HalLink> halLinks) {
        this.halLinks = halLinks;
    }

    @Override
    public String toString() {
        return "ResponseClearBank{" +
                "accounts=" + accounts +
                ", halLinks=" + halLinks +
                '}';
    }
}
