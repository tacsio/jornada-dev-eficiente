package io.tacsio.apipagamentos.service.gateway;

import java.math.BigDecimal;

public interface Gateway {

    boolean accept(CardInfo cardInfo);

    BigDecimal cost(BigDecimal value);

    GatewayResponse process(CardInfo cardInfo, BigDecimal value);
}
