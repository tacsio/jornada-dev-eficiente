package io.tacsio.apipagamentos.validator;

import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.validator.util.ValidationContext;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OfflinePaymentValidator implements ConstraintValidator<OfflinePayment, Long> {

    private ValidationContext ctx;
    private final EntityManager entityManager;

    public OfflinePaymentValidator(ValidationContext ctx, EntityManager entityManager) {
        this.ctx = ctx;
        this.entityManager = entityManager;
    }

    public boolean isValid(Long paymentMethodId, ConstraintValidatorContext context) {
        PaymentMethod paymentMethod = ctx.find(PaymentMethod.class, paymentMethodId.toString(),
                () -> entityManager.find(PaymentMethod.class, paymentMethodId));
        return (paymentMethod != null && !paymentMethod.getType().online);
    }
}
