package io.tacsio.mercadolivre.service.payment;

import io.tacsio.mercadolivre.model.order.Order;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

@Service
public class PaymentService {

    private Set<PaymentGateway> gateways;

    public PaymentService(Set<PaymentGateway> gateways) {
        this.gateways = gateways;
    }

    public URI processOrder(Order order) {
        var gateway = gateways.stream()
                .filter(it -> Objects.equals(it.type(), order.getSelectedGateway()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid gateway."));

        return gateway.processPayment(order);
    }

}
