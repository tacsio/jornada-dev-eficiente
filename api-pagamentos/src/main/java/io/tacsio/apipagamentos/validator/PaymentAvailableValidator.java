package io.tacsio.apipagamentos.validator;

import io.tacsio.apipagamentos.api.dto.form.OfflinePaymentForm;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.service.fraud.FraudAnalyzer;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PaymentAvailableValidator implements ConstraintValidator<PaymentAvailable, OfflinePaymentForm> {
    private EntityManager manager;
    private FraudAnalyzer fraudAnalyzer;

    public PaymentAvailableValidator(EntityManager manager, FraudAnalyzer fraudAnalyzer) {
        this.manager = manager;
        this.fraudAnalyzer = fraudAnalyzer;
    }

    public boolean isValid(OfflinePaymentForm form, ConstraintValidatorContext context) {
        System.out.println("LAST>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Restaurant restaurant = manager.find(Restaurant.class, form.restaurantId());
        User user = manager.find(User.class, form.userId());

        return user.availablePaymentMethods(restaurant, fraudAnalyzer)
                .filter(availableMethod -> Objects.equals(availableMethod.getId(), form.paymentMethodId()))
                .findFirst()
                .isPresent();
    }
}
