package br.com.deveficiente.bolaoapi.shared.validator;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class EmailCollectionValidator implements ConstraintValidator<EmailCollection, Set<String>> {

    public boolean isValid(Set<String> emails, ConstraintValidatorContext context) {
        if (emails == null) return false;

        EmailValidator validator = new EmailValidator();
        return !emails.stream()
                .anyMatch(email -> !validator.isValid(email, context));
    }
}
