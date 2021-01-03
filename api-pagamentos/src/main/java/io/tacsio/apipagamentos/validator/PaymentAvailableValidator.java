package io.tacsio.apipagamentos.validator;

import io.tacsio.apipagamentos.api.form.OfflinePaymentForm;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.service.fraud.FraudAnalyzer;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PaymentAvailableValidator implements ConstraintValidator<PaymentAvailable, OfflinePaymentForm> {
    private final EntityManager manager;
    private final FraudAnalyzer fraudAnalyzer;

    public PaymentAvailableValidator(EntityManager manager, FraudAnalyzer fraudAnalyzer) {
        this.manager = manager;
        this.fraudAnalyzer = fraudAnalyzer;
    }

    public boolean isValid(OfflinePaymentForm form, ConstraintValidatorContext context) {
        var restaurant = manager.find(Restaurant.class, form.restaurantId());
        var user = manager.find(User.class, form.userId());

        return user.availablePaymentMethods(restaurant, fraudAnalyzer)
                .anyMatch(availableMethod -> Objects.equals(availableMethod.getId(), form.paymentMethodId()));
    }
}
