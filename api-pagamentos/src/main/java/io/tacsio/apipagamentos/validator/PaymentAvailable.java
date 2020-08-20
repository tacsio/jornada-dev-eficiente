package io.tacsio.apipagamentos.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PaymentAvailableValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentAvailable {
    String message() default "Invalid payment method.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
