package io.tacsio.order.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import io.tacsio.order.Cliente;
import io.tacsio.order.ItemPedido;
import io.tacsio.order.Pedido;
import io.tacsio.order.validator.ValidOrderTotal;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@ValidOrderTotal
public class PedidoForm {

	@Valid
	public ClienteForm dadosCliente;

	@Positive
	public Double total;

	@Size(min = 1)
	public List<ItemPedidoForm> itensPedido;

	@Transactional
	public Pedido toModel() {
		Cliente cliente = this.dadosCliente.toModel();
		List<ItemPedido> itensPedido = this.convertItensPedidos();

		Pedido pedido = new Pedido(cliente, itensPedido, total);

		return pedido;
	}

	public List<ItemPedido> convertItensPedidos() {
		return this.itensPedido.stream()
			.map(ItemPedidoForm::toModel)
			.collect(Collectors.toList());
	}

}
