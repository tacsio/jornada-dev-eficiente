package io.tacsio.category.validator;

import io.tacsio.category.Categoria;

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
