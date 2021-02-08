package io.tacsio.apipagamentos.service.gateway;

import java.math.BigDecimal;

public interface Gateway {

    GatewayResponse process(CardInfo cardInfo, BigDecimal value);

    boolean accept(CardInfo cardInfo);

    BigDecimal cost(BigDecimal value);
}
