package io.tacsio.apipagamentos.api.representer;

import io.tacsio.apipagamentos.domain.PaymentMethod;

public class AvailablePaymentMethod {
    public final Long id;
    public final String description;

    public AvailablePaymentMethod(PaymentMethod paymentMethod) {
        this.id = paymentMethod.getId();
        this.description = paymentMethod.getDescription();
    }
}
