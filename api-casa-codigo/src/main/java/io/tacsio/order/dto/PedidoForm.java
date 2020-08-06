package io.tacsio.order.dto;

import javax.transaction.Transactional;
import javax.validation.Valid;

import io.tacsio.order.Cliente;
import io.tacsio.order.Pedido;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class PedidoForm {

	@Valid
	public ClienteForm dadosCliente;

	@Transactional
	public Pedido toModel() {
		Cliente cliente = this.dadosCliente.toModel();
		Pedido pedido = new Pedido(cliente);

		return pedido;
	}
}
