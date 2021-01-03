package io.tacsio.apipagamentos.validator;

import io.tacsio.apipagamentos.domain.PaymentMethod;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlinePaymentValidator implements ConstraintValidator<OnlinePayment, Long> {

    private final EntityManager entityManager;

    public OnlinePaymentValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean isValid(Long paymentMethodId, ConstraintValidatorContext context) {
        var paymentMethod = entityManager.find(PaymentMethod.class, paymentMethodId);
        return (paymentMethod != null && paymentMethod.getType().online);
    }
}
