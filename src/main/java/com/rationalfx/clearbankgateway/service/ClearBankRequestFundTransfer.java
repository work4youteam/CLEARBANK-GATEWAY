package com.rationalfx.clearbankgateway.service;

import com.rationalfx.clearbankgateway.config.ClearBankConfig;
import com.rationalfx.clearbankgateway.model.RequestClearBankFundTransfer;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Component
public class ClearBankRequestFundTransfer {

    @Autowired
    private ClearBankConfig clearBankConfig;

    public String getPaymentTransferBody(RequestClearBankFundTransfer requestClearBankFundTransfer) {

        String paymentTransferBody = clearBankConfig.getPaymentTransferBody();

        System.out.println("new Date() : " + new Date());
        SimpleDateFormat date1 = new SimpleDateFormat("MM-dd-yyyy");
        String strDate = date1.format(new Date());
        System.out.println("strDatestrDate : " + strDate);


        //  String virtualAccountBody = "{\"paymentInstructions\":[{\"paymentInstructionIdentification\":\"" + transactionId + "\",\"requestedExecutionDate\":\"" + strDate + "\",\"debtor\":{\"name\":\"" + debtorName + "\"},\"debtorAccount\":{\"identification\":{\"iban\":\"" + iban + "\"}},\"creditTransfers\":[{\"paymentIdentification\":{\"instructionIdentification\":\"" + transactionId + "\",\"endToEndIdentification\":\"" + endToEndIdentification + "\"},\"amount\":{\"instructedAmount\":" + bigDecimal + ",\"currency\":\"" + currencyCode + "\"},\"creditor\":{\"name\":\"" + creditorName + "\"},\"creditorAccount\":{\"identification\":{\"other\":{\"identification\":\"" + identification + "\",\"schemeName\":{\"proprietary\":\"" + proprietary + "\"}}}},\"remittanceInformation\":{\"structured\":{\"creditorReferenceInformation\":{\"reference\":\"" + reference + "\"}}}}]}]}";
        paymentTransferBody = paymentTransferBody.replace("$UUID$", requestClearBankFundTransfer.getReference());
        paymentTransferBody = paymentTransferBody.replace("$EXECUTIONDATE$", strDate);
        paymentTransferBody = paymentTransferBody.replace("$DEBTOR_NAME$", requestClearBankFundTransfer.getDebtorName());
        paymentTransferBody = paymentTransferBody.replace("$CURRENCY$", requestClearBankFundTransfer.getCurrency());
        paymentTransferBody = paymentTransferBody.replace("$IBANFROM$", requestClearBankFundTransfer.getFromIban());
        paymentTransferBody = paymentTransferBody.replace("$IBANTO$", requestClearBankFundTransfer.getToIban());
        paymentTransferBody = paymentTransferBody.replace("$AMOUNT$", (CharSequence) requestClearBankFundTransfer.getAmount());
        paymentTransferBody = paymentTransferBody.replace("$CREDITOR_NAME$", requestClearBankFundTransfer.getCreditorName());
        paymentTransferBody = paymentTransferBody.replace("$REFERENCE$", requestClearBankFundTransfer.getReference());
        paymentTransferBody = paymentTransferBody.replace("$PROPRIETARY$", requestClearBankFundTransfer.getProprietary());

        return paymentTransferBody;
    }
}
