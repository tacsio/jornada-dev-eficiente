package io.tacsio.apipagamentos.validator;

import io.tacsio.apipagamentos.service.order.Order;
import io.tacsio.apipagamentos.service.order.OrderService;
import io.tacsio.apipagamentos.validator.util.ValidationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderValidator implements ConstraintValidator<ValidOrder, Long> {

    private final ValidationContext ctx;
    private final OrderService orderService;

    public OrderValidator(ValidationContext ctx, OrderService orderService) {
        this.ctx = ctx;
        this.orderService = orderService;
    }

    public boolean isValid(Long orderId, ConstraintValidatorContext context) {
        Order order = this.ctx.find(Order.class, orderId.toString(),
                () -> orderService.getOrder(orderId));
        return order != null;
    }
}
