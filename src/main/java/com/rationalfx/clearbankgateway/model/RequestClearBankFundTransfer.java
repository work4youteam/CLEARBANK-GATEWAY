package com.rationalfx.clearbankgateway.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class RequestClearBankFundTransfer {
    private String debtorName;
    private String creditorName;
    private String currency;
    private String fromIban;
    private String toIban;
    private BigDecimal amount;
    private String proprietary;
    private String reference;

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFromIban() {
        return fromIban;
    }

    public void setFromIban(String fromIban) {
        this.fromIban = fromIban;
    }

    public String getToIban() {
        return toIban;
    }

    public void setToIban(String toIban) {
        this.toIban = toIban;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProprietary() {
        return proprietary;
    }

    public void setProprietary(String proprietary) {
        this.proprietary = proprietary;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "RequestClearBankFundTransfer{" +
                "debtorName='" + debtorName + '\'' +
                ", creditorName='" + creditorName + '\'' +
                ", currency='" + currency + '\'' +
                ", fromIban='" + fromIban + '\'' +
                ", toIban='" + toIban + '\'' +
                ", amount=" + amount +
                ", proprietary='" + proprietary + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}
