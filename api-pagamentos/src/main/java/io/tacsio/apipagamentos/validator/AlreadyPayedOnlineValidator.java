package io.tacsio.apipagamentos.validator;

import io.tacsio.apipagamentos.api.form.OnlinePaymentForm;
import io.tacsio.apipagamentos.domain.data.TransactionRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlreadyPayedOnlineValidator implements ConstraintValidator<AlreadyPayedOnline, OnlinePaymentForm> {
    private final TransactionRepository transactionRepository;

    public AlreadyPayedOnlineValidator(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean isValid(OnlinePaymentForm form, ConstraintValidatorContext context) {
        var paymentTransaction =
            transactionRepository.find(form.orderId(), form.userId(), form.restaurantId(), form.paymentMethodId());
        return paymentTransaction == null;
    }
}
