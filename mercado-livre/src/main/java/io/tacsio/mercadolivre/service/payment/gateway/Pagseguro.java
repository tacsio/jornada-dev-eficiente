package io.tacsio.mercadolivre.service.payment.gateway;

import io.tacsio.mercadolivre.model.order.Order;
import io.tacsio.mercadolivre.service.payment.Gateway;
import io.tacsio.mercadolivre.service.payment.PaymentGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class Pagseguro implements PaymentGateway {

    @Value("${application.payment.gateway.pagseguro}")
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
        return Gateway.PAG_SEGURO;
    }
}
