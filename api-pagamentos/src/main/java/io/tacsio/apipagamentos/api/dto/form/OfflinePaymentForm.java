package io.tacsio.apipagamentos.api.dto.form;

import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.validator.Exists;
import io.tacsio.apipagamentos.validator.LateValidation;
import io.tacsio.apipagamentos.validator.OfflinePayment;
import io.tacsio.apipagamentos.validator.PaymentAvailable;

import javax.validation.GroupSequence;

@GroupSequence({OfflinePaymentForm.class, LateValidation.class})
@PaymentAvailable(groups = {LateValidation.class})
public record OfflinePaymentForm(
        @OfflinePayment Long paymentMethodId,
        @Exists(entityClass = User.class) Long userId,
        @Exists(entityClass = Restaurant.class) Long restaurantId,
        Long orderId) {
}
