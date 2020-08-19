package io.tacsio.apipagamentos.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OfflinePaymentValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OfflinePayment {
    String message() default "the payment method isn't offline";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
