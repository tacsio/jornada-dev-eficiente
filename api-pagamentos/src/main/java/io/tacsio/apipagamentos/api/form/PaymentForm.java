package io.tacsio.apipagamentos.api.form;

public interface PaymentForm {
    Long userId();

    Long restaurantId();

    Long paymentMethodId();
}
