package io.tacsio.country.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ExistsCountryValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ExistsCountry {
	String message() default "{Country.not.exists}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
