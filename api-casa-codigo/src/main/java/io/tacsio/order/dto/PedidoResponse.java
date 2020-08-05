package io.tacsio.order.dto;

import io.tacsio.country.dto.PaisResponse;
import io.tacsio.order.Cliente;
import io.tacsio.order.Pedido;
import io.tacsio.state.dto.EstadoResponse;
import lombok.Getter;
import lombok.ToString;

@Getter
public class PedidoResponse {

	private final ClienteResponse dadosCliente;

	public PedidoResponse(Pedido pedido) {
		this.dadosCliente = new ClienteResponse(pedido.getCliente());
	}

	@Getter
	@ToString
	public class ClienteResponse {

		private final String email;
		private final String nome;
		private final String sobrenome;
		private final String documento;
		private final String cep;
		private final String endereco;
		private final String complemento;
		private final String cidade;
		private final PaisResponse pais;
		private EstadoResponse estado;

		public ClienteResponse(Cliente cliente) {
			this.email = cliente.getEmail();
			this.nome = cliente.getNome();
			this.sobrenome = cliente.getSobrenome();
			this.documento = cliente.getDocumento();
			this.cep = cliente.getCep();
			this.endereco = cliente.getEndereco();
			this.complemento = cliente.getComplemento();
			this.cidade = cliente.getCidade();

			this.pais = new PaisResponse(cliente.getPais());

			if (cliente.getEstado() != null) {
				this.estado = new EstadoResponse(cliente.getEstado());
			}
		}
	}
}