package io.tacsio.apipagamentos.validator;

import io.tacsio.apipagamentos.domain.PaymentMethod;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OfflinePaymentValidator implements ConstraintValidator<OfflinePayment, Long> {

    private final EntityManager entityManager;

    public OfflinePaymentValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean isValid(Long paymentMethodId, ConstraintValidatorContext context) {
        System.out.println("FIRST OFFLINE>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        PaymentMethod paymentMethod = entityManager.find(PaymentMethod.class, paymentMethodId);
        return (paymentMethod != null && !paymentMethod.getType().online);
    }
}
