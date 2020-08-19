package io.tacsio.apipagamentos.api.dto.representer;

import io.tacsio.apipagamentos.domain.PaymentMethod;

public class AvailablePaymentMethod {
    private final Long id;
    private final String description;

    public AvailablePaymentMethod(PaymentMethod paymentMethod) {
        this.id = paymentMethod.getId();
        this.description = paymentMethod.getDescription();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
