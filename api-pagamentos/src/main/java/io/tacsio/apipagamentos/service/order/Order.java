package io.tacsio.apipagamentos.service.order;

import java.math.BigDecimal;

public class Order {
    public final Long id;
    public final BigDecimal value;

    public Order(Long id, BigDecimal value) {
        this.id = id;
        this.value = value;
    }
}
