package io.tacsio.mercadolivre.service.order;

public class ProductUnavailableException extends RuntimeException {
    public ProductUnavailableException(String message) {
        super(message);
    }
}
