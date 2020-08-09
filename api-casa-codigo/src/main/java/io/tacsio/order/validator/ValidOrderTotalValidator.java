package io.tacsio.order.validator;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.order.ItemPedido;
import io.tacsio.order.dto.PedidoForm;

@ApplicationScoped
public class ValidOrderTotalValidator implements ConstraintValidator<ValidOrderTotal, PedidoForm> {

	@Override
	public boolean isValid(PedidoForm form, ConstraintValidatorContext context) {
		return validateOrderPrice(form);
	}

	@Transactional
	private boolean validateOrderPrice(PedidoForm form) {
		List<ItemPedido> itensPedido = form.convertItensPedidos();

		double totalItens = itensPedido.stream()
			.mapToDouble(ItemPedido::valorItem)
			.sum();

		return form.total == totalItens;
	}

}
