package io.tacsio.apipagamentos.validator;

import io.tacsio.apipagamentos.service.order.OrderService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderValidator implements ConstraintValidator<ValidOrder, Long> {

    private final OrderService orderService;

    public OrderValidator(OrderService orderService) {
        this.orderService = orderService;
    }

    public boolean isValid(Long orderId, ConstraintValidatorContext context) {
        return orderService.getOrder(orderId) != null;
    }
}
