package br.com.deveficiente.bolaoapi.services.user.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LoginValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueLogin {
    String message() default "login already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
