package com.rationalfx.clearbankgateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "clearbank")
@PropertySource({"classpath:application_${targetenv}.properties"})
public class ClearBankConfig {
    private String env;
    private String tokenBearerPostClearBank;
    private String securityToken;
    private String clearbankgettransactionurl;
    private String inputsecuritytoken;
    private String privateKeyPath;
    private String tokenBearerPost;
    private String urlVirtualAccountCreate;
    private String parentcollection;
    private String digitalsignatureapi;
    private String paymentTransferBody;
    private String clearBankHeaderAccountDetailsURL;
    private String clearBankFundURL;
    private String clearBankInactiveAccount;

    public String getUrlVirtualAccountCreate() {
        return urlVirtualAccountCreate;
    }

    public void setUrlVirtualAccountCreate(String urlVirtualAccountCreate) {
        this.urlVirtualAccountCreate = urlVirtualAccountCreate;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getTokenBearerPostClearBank() {
        return tokenBearerPostClearBank;
    }

    public void setTokenBearerPostClearBank(String tokenBearerPostClearBank) {
        this.tokenBearerPostClearBank = tokenBearerPostClearBank;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getClearbankgettransactionurl() {
        return clearbankgettransactionurl;
    }

    public void setClearbankgettransactionurl(String clearbankgettransactionurl) {
        this.clearbankgettransactionurl = clearbankgettransactionurl;
    }

    public String getInputsecuritytoken() {
        return inputsecuritytoken;
    }

    public void setInputsecuritytoken(String inputsecuritytoken) {
        this.inputsecuritytoken = inputsecuritytoken;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }

    public String getTokenBearerPost() {
        return tokenBearerPost;
    }

    public void setTokenBearerPost(String tokenBearerPost) {
        this.tokenBearerPost = tokenBearerPost;
    }

    public String getParentcollection() {
        return parentcollection;
    }

    public void setParentcollection(String parentcollection) {
        this.parentcollection = parentcollection;
    }

    public String getDigitalsignatureapi() {
        return digitalsignatureapi;
    }

    public void setDigitalsignatureapi(String digitalsignatureapi) {
        this.digitalsignatureapi = digitalsignatureapi;
    }

    public String getPaymentTransferBody() {
        return paymentTransferBody;
    }

    public void setPaymentTransferBody(String paymentTransferBody) {
        this.paymentTransferBody = paymentTransferBody;
    }

    public String getClearBankHeaderAccountDetailsURL() {
        return clearBankHeaderAccountDetailsURL;
    }

    public void setClearBankHeaderAccountDetailsURL(String clearBankHeaderAccountDetailsURL) {
        this.clearBankHeaderAccountDetailsURL = clearBankHeaderAccountDetailsURL;
    }

    public String getClearBankFundURL() {
        return clearBankFundURL;
    }

    public void setClearBankFundURL(String clearBankFundURL) {
        this.clearBankFundURL = clearBankFundURL;
    }

    public String getClearBankInactiveAccount() {
        return clearBankInactiveAccount;
    }

    public void setClearBankInactiveAccount(String clearBankInactiveAccount) {
        this.clearBankInactiveAccount = clearBankInactiveAccount;
    }

    @Override
    public String toString() {
        return "ClearBankConfig{" +
                "env='" + env + '\'' +
                ", tokenBearerPostClearBank='" + tokenBearerPostClearBank + '\'' +
                ", securityToken='" + securityToken + '\'' +
                ", clearbankgettransactionurl='" + clearbankgettransactionurl + '\'' +
                ", inputsecuritytoken='" + inputsecuritytoken + '\'' +
                ", privateKeyPath='" + privateKeyPath + '\'' +
                ", tokenBearerPost='" + tokenBearerPost + '\'' +
                ", urlVirtualAccountCreate='" + urlVirtualAccountCreate + '\'' +
                ", parentcollection='" + parentcollection + '\'' +
                ", digitalsignatureapi='" + digitalsignatureapi + '\'' +
                ", paymentTransferBody='" + paymentTransferBody + '\'' +
                ", clearBankHeaderAccountDetailsURL='" + clearBankHeaderAccountDetailsURL + '\'' +
                ", clearBankFundURL='" + clearBankFundURL + '\'' +
                ", clearBankInactiveAccount='" + clearBankInactiveAccount + '\'' +
                '}';
    }
}
