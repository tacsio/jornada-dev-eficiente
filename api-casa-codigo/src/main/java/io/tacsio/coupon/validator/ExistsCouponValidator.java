package io.tacsio.coupon.validator;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.coupon.Cupom;

@ApplicationScoped
public class ExistsCouponValidator implements ConstraintValidator<ExistsCoupon, String> {

	@Override
	public boolean isValid(String  codigo, ConstraintValidatorContext context) {
		Optional<Cupom> cupom = Cupom.find("codigo", codigo).firstResultOptional();

		return cupom.isPresent();
	}

}