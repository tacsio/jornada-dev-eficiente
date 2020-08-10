package io.tacsio.order.coupon.validator;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.order.coupon.Cupom;

@ApplicationScoped
public class UniqueCodeValidator implements ConstraintValidator<UniqueCode, String> {

	@Override
	public boolean isValid(String code, ConstraintValidatorContext context) {
		Optional<Cupom> cupom = Cupom.find("codigo", code)
			.singleResultOptional();

		return cupom.isEmpty();
	}

}