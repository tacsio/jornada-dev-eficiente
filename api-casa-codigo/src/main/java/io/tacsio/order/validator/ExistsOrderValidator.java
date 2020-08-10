package io.tacsio.order.validator;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.order.Pedido;

@ApplicationScoped
public class ExistsOrderValidator implements ConstraintValidator<ExistsOrder, Long> {

	@Override
	public boolean isValid(Long id, ConstraintValidatorContext context) {
		Optional<Pedido> pedido = Pedido.findByIdOptional(id);

		return pedido.isPresent();
	}

}