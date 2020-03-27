package br.com.deveficiente.bolaoapi.shared.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = DatabaseTeamsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DatabaseTeams {

    String message() default "invalid team id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
