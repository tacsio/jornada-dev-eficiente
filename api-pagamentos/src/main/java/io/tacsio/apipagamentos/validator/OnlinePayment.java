package io.tacsio.apipagamentos.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OnlinePaymentValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlinePayment {
    String message() default "the payment method isn't valid for online transactions.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
