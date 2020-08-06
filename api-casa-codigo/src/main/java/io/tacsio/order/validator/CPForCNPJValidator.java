package io.tacsio.order.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

public class CPForCNPJValidator implements ConstraintValidator<CPForCNPJ, String> {

	private CPFValidator cpfValidator;
	private CNPJValidator cnpjValidator;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return cpfValidator.isValid(value, context) || cnpjValidator.isValid(value, context);
	}

	@Override
	public void initialize(CPForCNPJ constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);

		this.cpfValidator = new CPFValidator();
		this.cnpjValidator = new CNPJValidator();

		this.cpfValidator.initialize(null);
		this.cnpjValidator.initialize(null);
	}

}
