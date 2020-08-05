package io.tacsio.order.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Documented
@Constraint(validatedBy = CPForCNPJValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface CPForCNPJ {
	
}