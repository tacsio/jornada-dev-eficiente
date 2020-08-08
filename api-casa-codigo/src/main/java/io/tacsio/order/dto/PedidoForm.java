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
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
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
		
		this.validateOrderPrice(itensPedido, total);
		
		Pedido pedido = new Pedido(cliente, itensPedido, total);

		return pedido;
	}

	private List<ItemPedido> convertItensPedidos () {
		return this.itensPedido.stream()
			.map(ItemPedidoForm::toModel)
			.collect(Collectors.toList());
	}

	private void validateOrderPrice(List<ItemPedido> itensPedido, Double total) {
		double totalItens = itensPedido.stream()
			.mapToDouble(ItemPedido::valorItem)
			.sum();

		if(total != totalItens) {
			throw new IllegalArgumentException("Order.total.invalid");
		}
	}

}
