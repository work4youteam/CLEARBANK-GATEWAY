package com.rationalfx.clearbankgateway.config;

import java.math.BigDecimal;

public interface Constant {
    public static String CLEARBANK = "CLEARBANK";
    public static String AUTHORIZATION = "Authorization";
    public static final String ERROR = "ERROR";
    public static final String INVALID_TOKEN = "Invalid Token";
    public static final String AUTH_BEARER_TOKEN_MISSING = "Authorization Bearer Token Missing";
    public static final String GBP = "GBP";

    public static final String UNIQUE_KEY_PREFIX = "RATIONALFX-";

    public static final String TRANSACTIONSETTLED = "TransactionSettled";

    public static final String VIRTUALACCOUNTCREATED = "VirtualAccountCreated";

    public static final String VIRTUALACCOUNTCREATIONFAILED = "VirtualAccountCreationFailed";


    public static final String WEBHOOK_CLEARBANK = "WEBHOOK/CLEARBANK";

    public static final String STATUS_PENDING_RESPONSE = "Pending Response";

    public static final String COMPLETED = "COMPLETED";

    public static final String CREATED = "CREATED";

    public static final String MESSAGE_NO_REQUESTID_FOUND_IN_DB = "No RequestID found in db";


    public static final String REQUIRED_INPUT_FIELDS_MISSING = "Required Input Fields Missing";

    public static final String BAD_GATEWAY_ERROR = "Bad gateway Error";

    public static final String VIRTUAL_ACCOUNT_CREATION_FAILED = "Virtual Account Creation Failed";


    public static final String VERIFY_DIGSIGNATURE_FAILED = "Verify Digital Signature Failed";

    public static final String PENDING = "PENDING";
    public static final String CREDIT = "Credit";
    public static final String DEBIT = "Debit";
    public static BigDecimal BALANCE = BigDecimal.valueOf(00.000000);
    BigDecimal AMOUNT_LIMIT = BigDecimal.valueOf(250000.00000);


}
