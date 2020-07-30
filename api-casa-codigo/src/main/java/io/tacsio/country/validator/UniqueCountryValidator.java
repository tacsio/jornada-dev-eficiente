package io.tacsio.country.validator;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.country.Pais;

@ApplicationScoped
public class UniqueCountryValidator implements ConstraintValidator<UniqueCountry, String> {

	@Override
	public boolean isValid(String nome, ConstraintValidatorContext context) {
		return Pais.find("nome", nome).firstResultOptional().isEmpty();
	}
}
