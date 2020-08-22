package io.tacsio.apipagamentos.api.dto.form;

import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.Transaction;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.service.order.Order;
import io.tacsio.apipagamentos.validator.*;
import io.tacsio.apipagamentos.validator.util.ValidationContext;

import javax.validation.GroupSequence;

@GroupSequence({OfflinePaymentForm.class, LateValidation.class})
@PaymentAvailable(groups = {LateValidation.class})
public record OfflinePaymentForm(
        @OfflinePayment Long paymentMethodId,
        @Exists(entityClass = User.class) Long userId,
        @Exists(entityClass = Restaurant.class) Long restaurantId,
        @ValidOrder Long orderId) {


    public Transaction getTransaction(ValidationContext context) {
        var user = context.get(User.class, userId);
        var restaurant = context.get(Restaurant.class, restaurantId);
        var paymentMethod = context.get(PaymentMethod.class, paymentMethodId);
        var order = context.get(Order.class, orderId);

        return new Transaction(orderId, order.value, user, restaurant, paymentMethod, "");
    }
}
