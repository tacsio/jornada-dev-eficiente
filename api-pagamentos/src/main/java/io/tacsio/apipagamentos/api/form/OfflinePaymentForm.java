package io.tacsio.apipagamentos.api.form;

import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.Transaction;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.service.order.OrderService;
import io.tacsio.apipagamentos.validator.*;

import javax.persistence.EntityManager;
import javax.validation.GroupSequence;

@GroupSequence({OfflinePaymentForm.class, LateValidation.class})
@PaymentAvailable(groups = {LateValidation.class})
public record OfflinePaymentForm(
        @ValidOrder Long orderId,
        @OfflinePayment Long paymentMethodId,
        @Exists(entityClass = User.class) Long userId,
        @Exists(entityClass = Restaurant.class) Long restaurantId) {


    public Transaction getTransaction(EntityManager em, OrderService orderService) {
        var user = em.find(User.class, userId);
        var restaurant = em.find(Restaurant.class, restaurantId);
        var paymentMethod = em.find(PaymentMethod.class, paymentMethodId);
        var order = orderService.getOrder(orderId);

        return new Transaction(orderId, order.value, user, restaurant, paymentMethod, "");
    }
}
