package io.tacsio.mercadolivre.service.order;

import io.tacsio.mercadolivre.model.order.Order;

import java.net.URI;

public class ProcessResult {
    public final Order order;
    public final URI gatewayURI;

    public ProcessResult(Order order, URI gatewayURI) {
        this.order = order;
        this.gatewayURI = gatewayURI;
    }
}
