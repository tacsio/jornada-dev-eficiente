package io.tacsio.mercadolivre.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProductOwnerValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductOwner {
    String message() default "you not owns that product.";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
