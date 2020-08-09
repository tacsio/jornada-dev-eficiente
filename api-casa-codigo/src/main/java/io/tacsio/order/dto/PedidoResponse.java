package io.tacsio.order.dto;

import java.util.List;
import java.util.stream.Collectors;

import io.tacsio.country.dto.PaisResponse;
import io.tacsio.order.Cliente;
import io.tacsio.order.ItemPedido;
import io.tacsio.order.Pedido;
import io.tacsio.country.state.dto.EstadoResponse;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PedidoResponse {

	private final long idPedido;
	private final ClienteResponse dadosCliente;
	private final double total;
	private final List<ItemPedidoResponse> itensPedido;

	public PedidoResponse(Pedido pedido) {
		this.idPedido = pedido.getId();

		this.dadosCliente = new ClienteResponse(pedido.getCliente());

		this.total = pedido.getTotal();
		this.itensPedido = pedido.getItensPedido().stream()
			.map(ItemPedidoResponse::new)
			.collect(Collectors.toList());
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

	@Getter
	@ToString
	public class ItemPedidoResponse {
		private final String livro;
		private final int quantidade;
		private final double preco;

		public ItemPedidoResponse(ItemPedido itemPedido) {
			this.livro = itemPedido.getLivro().getTitulo();
			this.quantidade = itemPedido.getQuantidade();
			this.preco = itemPedido.getPreco();
		}

	}
}