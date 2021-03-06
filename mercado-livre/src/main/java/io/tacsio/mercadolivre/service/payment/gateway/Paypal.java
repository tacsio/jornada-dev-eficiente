package io.tacsio.mercadolivre.service.payment.gateway;

import io.tacsio.mercadolivre.model.order.Order;
import io.tacsio.mercadolivre.service.payment.Gateway;
import io.tacsio.mercadolivre.service.payment.PaymentGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class Paypal implements PaymentGateway {

    @Value("${application.payment.gateway.paypal}")
    private String template;

    @Value("${application.payment.redirect}")
    private String redirect;


    @Override
    public URI processPayment(Order order) {
        var transactionId = order.getTransactionId();
        var uri = UriComponentsBuilder.fromUriString(template)
                .buildAndExpand(transactionId, redirect)
                .toUri();

        return uri;
    }

    @Override
    public Gateway type() {
        return Gateway.PAYPAL;
    }
}
