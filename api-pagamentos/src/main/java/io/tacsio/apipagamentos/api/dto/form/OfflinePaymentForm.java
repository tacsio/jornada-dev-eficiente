package io.tacsio.apipagamentos.api.dto.form;

import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.validator.Exists;
import io.tacsio.apipagamentos.validator.OfflinePayment;

public record OfflinePaymentForm(
        @OfflinePayment Long paymentMethodId,
        @Exists(entityClass = User.class) Long userId,
        @Exists(entityClass = Restaurant.class) Long restaurantId,
        Long orderId) {
}
