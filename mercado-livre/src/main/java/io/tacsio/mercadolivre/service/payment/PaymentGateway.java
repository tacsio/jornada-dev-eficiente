package io.tacsio.mercadolivre.service.payment;

import io.tacsio.mercadolivre.model.order.Order;

import java.net.URI;

public interface PaymentGateway {

    URI processPayment(Order order);

    Gateway type();
}
