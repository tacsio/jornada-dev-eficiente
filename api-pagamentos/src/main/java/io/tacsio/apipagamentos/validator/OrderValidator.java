package io.tacsio.apipagamentos.validator;

import io.tacsio.apipagamentos.service.order.OrderResponse;
import io.tacsio.apipagamentos.service.order.OrderService;
import io.tacsio.apipagamentos.validator.util.ValidationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderValidator implements ConstraintValidator<Order, Long> {

    private ValidationContext ctx;
    private OrderService orderService;

    public OrderValidator(ValidationContext ctx, OrderService orderService) {
        this.ctx = ctx;
        this.orderService = orderService;
    }

    public boolean isValid(Long orderId, ConstraintValidatorContext context) {
        OrderResponse order = this.ctx.find(OrderResponse.class, orderId.toString(),
                () -> orderService.getOrder(orderId));
        return order != null;
    }
}
