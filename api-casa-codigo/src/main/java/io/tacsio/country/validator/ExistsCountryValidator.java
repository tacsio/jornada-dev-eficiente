package io.tacsio.country.validator;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.country.Pais;

@ApplicationScoped
public class ExistsCountryValidator implements ConstraintValidator<ExistsCountry, Long> {

	@Override
	public boolean isValid(Long id, ConstraintValidatorContext context) {
		return Pais.find("id", id).firstResultOptional().isPresent();
	}
}
