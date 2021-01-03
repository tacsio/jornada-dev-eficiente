package io.tacsio.apipagamentos.service.gateway;

public interface Gateway {
    default boolean acceptPayment() {
        return this.accept();
    }

    boolean accept();

    void process();
}
