package io.tacsio.apipagamentos.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AlreadyPayedOnlineValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AlreadyPayedOnline {
    String message() default "Order already payed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
