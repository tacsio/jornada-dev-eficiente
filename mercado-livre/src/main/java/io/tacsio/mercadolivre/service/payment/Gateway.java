package io.tacsio.mercadolivre.service.payment;

public enum Gateway {
    PAYPAL("paypal"), PAG_SEGURO("pagseguro");

    private String name;

    Gateway(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
