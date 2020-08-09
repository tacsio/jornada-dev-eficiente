package io.tacsio.country.state.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UniqueStateValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface UniqueState {
	String message() default "{State.name.duplicated}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
