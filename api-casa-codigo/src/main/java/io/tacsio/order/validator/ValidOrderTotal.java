package io.tacsio.order.validator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ValidOrderTotalValidator.class)
@Target({ TYPE })
@Retention(RUNTIME)
public @interface ValidOrderTotal {
	String message() default "{Order.total.invalid}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}