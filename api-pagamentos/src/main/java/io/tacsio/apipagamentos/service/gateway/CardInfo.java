package io.tacsio.apipagamentos.service.gateway;

import io.tacsio.apipagamentos.domain.PaymentMethod;

public class CardInfo {
    public final String cardNumber;
    public final String securityCode;
    public final PaymentMethod.Brand brand;

    public CardInfo(String cardNumber, String securityCode, PaymentMethod.Brand brand) {
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.brand = brand;
    }
}
