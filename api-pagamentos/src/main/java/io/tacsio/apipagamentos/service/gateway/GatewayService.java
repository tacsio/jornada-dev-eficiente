package io.tacsio.apipagamentos.service.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GatewayService {
    private final Logger log = LoggerFactory.getLogger(GatewayService.class);
    private final GatewaySelector gatewaySelector;

    public GatewayService(GatewaySelector gatewaySelector) {
        this.gatewaySelector = gatewaySelector;
    }

    public GatewayResponse processPayment(CardInfo cardInfo, BigDecimal value) {
        var gateways = gatewaySelector.availableGateways(cardInfo, value);

        var paymentProcessed = false;
        var gatewayResponse = GatewayResponse.failed();

        while (!paymentProcessed && !gateways.isEmpty()) {
            var gateway = gateways.poll();
            gatewayResponse = gatewayPayment(cardInfo, value, gateway);
            paymentProcessed = gatewayResponse.success;
        }
        return gatewayResponse;
    }

    private GatewayResponse gatewayPayment(CardInfo cardInfo, BigDecimal value, Gateway gateway) {
        try {
            return gateway.process(cardInfo, value);
        } catch (RuntimeException e) {
            log.error("Gateway (" + gateway.getClass().getName() + ") error: " + e.getMessage());
            return GatewayResponse.failed();
        }
    }
}
