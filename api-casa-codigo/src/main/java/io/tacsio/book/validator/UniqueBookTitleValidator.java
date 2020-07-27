package io.tacsio.book.validator;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.book.Livro;

@ApplicationScoped
public class UniqueBookTitleValidator implements ConstraintValidator<UniqueBookTitle, String> {

	@Override
	public boolean isValid(String titulo, ConstraintValidatorContext context) {
		return Livro.find("titulo", titulo).firstResultOptional().isEmpty();
	}
}
