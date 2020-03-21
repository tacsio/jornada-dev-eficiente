package br.com.deveficiente.bolaoapi.shared.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "value already exists";

    Class<?> entityClass();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
