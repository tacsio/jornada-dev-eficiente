package br.com.deveficiente.bolaoapi.shared.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = MatchTeamsRequestValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MatchTeamsRequest {

    String message() default "Home Team and Visiting Team should not be the same.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
