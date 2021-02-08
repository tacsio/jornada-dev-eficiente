package io.tacsio.apipagamentos.service.gateway.impl;

import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.service.gateway.CardInfo;
import io.tacsio.apipagamentos.service.gateway.Gateway;
import io.tacsio.apipagamentos.service.gateway.GatewayResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class SaoriGateway implements Gateway {

    private final BigDecimal tax = BigDecimal.valueOf(0.05);

    private final Set<PaymentMethod.Brand> acceptedBrands = Set.of(
        PaymentMethod.Brand.VISA, PaymentMethod.Brand.MASTERCARD);

    @Override
    public GatewayResponse process(CardInfo cardInfo, BigDecimal value) {
        return null;
    }

    @Override
    public boolean accept(CardInfo cardInfo) {
        return acceptedBrands.contains(cardInfo.brand);
    }

    @Override
    public BigDecimal cost(BigDecimal value) {
        System.out.println("Saori: " + value.multiply(tax));
        return value.multiply(tax);
    }
}
