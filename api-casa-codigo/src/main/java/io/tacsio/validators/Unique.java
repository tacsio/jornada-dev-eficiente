package io.tacsio.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Unique {
	String message() default "value already exists";

	Class<?> entityClass();

	String entityField();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
