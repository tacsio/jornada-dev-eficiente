package io.tacsio.book.validator;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.book.Livro;


@ApplicationScoped
public class ExistsBookValidator implements ConstraintValidator<ExistsBook, Long> {

	@Override
	public boolean isValid(Long id, ConstraintValidatorContext context) {
		return Livro.find("id", id).firstResultOptional().isPresent();
	}
}
