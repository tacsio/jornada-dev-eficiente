package io.tacsio.apipagamentos.api.dto;

import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.validator.Exists;

public record AvailablePaymentForm(
        @Exists(entityClass = Restaurant.class) Long restaurantId,
        @Exists(entityClass = User.class) Long userId) {
}
