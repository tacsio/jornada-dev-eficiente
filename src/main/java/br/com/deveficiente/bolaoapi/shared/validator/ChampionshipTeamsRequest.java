package br.com.deveficiente.bolaoapi.shared.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ChampionshipTeamsRequestValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChampionshipTeamsRequest {

    String message() default "invalid number of teams";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
