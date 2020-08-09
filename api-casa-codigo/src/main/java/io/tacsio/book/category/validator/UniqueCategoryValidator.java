package io.tacsio.book.category.validator;

import io.tacsio.book.category.Categoria;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@ApplicationScoped
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {

	@Override
	public boolean isValid(String nome, ConstraintValidatorContext context) {
		return Categoria.find("nome", nome).firstResultOptional().isEmpty();
	}
}
