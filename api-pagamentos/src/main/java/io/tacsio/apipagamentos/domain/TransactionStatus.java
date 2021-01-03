package io.tacsio.apipagamentos.domain;

public enum TransactionStatus {
    //offline
    AWAITING_PAYMENT_CONFIRMATION, CONCLUDED,

    //online
    FAILED, SUCCEED
}
