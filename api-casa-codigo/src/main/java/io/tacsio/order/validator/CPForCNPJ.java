package io.tacsio.order.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CPForCNPJValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface CPForCNPJ {
	String message() default "{Order.document.invalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}