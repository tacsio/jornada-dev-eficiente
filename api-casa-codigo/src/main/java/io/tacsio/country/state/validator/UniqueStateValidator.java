package io.tacsio.country.state.validator;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.country.state.Estado;

@ApplicationScoped
public class UniqueStateValidator implements ConstraintValidator<UniqueState, String> {

	@Override
	public boolean isValid(String nome, ConstraintValidatorContext context) {
		return Estado.find("nome", nome).firstResultOptional().isEmpty();
	}
}
