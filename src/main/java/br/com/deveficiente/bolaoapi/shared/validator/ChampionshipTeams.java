package br.com.deveficiente.bolaoapi.shared.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ChampionshipTeamsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChampionshipTeams {

    String message() default "invalid number of teams";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
