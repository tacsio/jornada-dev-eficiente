package io.tacsio.apipagamentos.service.order;

import java.math.BigDecimal;

public class OrderResponse {
    public final Long id;
    public final BigDecimal value;

    public OrderResponse(Long id, BigDecimal value) {
        this.id = id;
        this.value = value;
    }
}
