package io.tacsio.book.category.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UniqueCategoryValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface UniqueCategory {
	String message() default "{Category.name.duplicated}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
