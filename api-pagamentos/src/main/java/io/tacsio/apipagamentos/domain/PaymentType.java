package io.tacsio.apipagamentos.domain;

public enum PaymentType {
    CARD(true),
    MONEY(false),
    CARD_MACHINE(false),
    CHECK(false);

    private final boolean online;

    PaymentType(boolean online) {
        this.online = online;
    }

    public boolean acceptOnline() {
        return online;
    }
}
