package io.tacsio.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ExistsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {
    String message() default "not found";

    Class<?> entityClass();

    String entityField() default "id";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
