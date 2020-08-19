package io.tacsio.apipagamentos.service.fraud;

import io.tacsio.apipagamentos.domain.PaymentClient;
import io.tacsio.apipagamentos.domain.PaymentMethod;

public interface FraudChecker {
    <Payer extends PaymentClient> boolean accept(Payer payer, PaymentMethod paymentMethod);
}
