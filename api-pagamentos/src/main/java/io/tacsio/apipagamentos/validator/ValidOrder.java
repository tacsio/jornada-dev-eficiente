package io.tacsio.apipagamentos.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OrderValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrder {
    String message() default "Invalid order id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
