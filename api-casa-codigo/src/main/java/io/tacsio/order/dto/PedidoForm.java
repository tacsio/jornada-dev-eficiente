package io.tacsio.order.dto;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.tacsio.country.Pais;
import io.tacsio.country.validator.ExistsCountry;
import io.tacsio.order.Cliente;
import io.tacsio.order.Pedido;
import io.tacsio.order.validator.CPForCNPJ;
import io.tacsio.state.Estado;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PedidoForm {

	@Valid
	public ClienteForm dadosCliente;

	@Transactional
	public Pedido toModel() {
		Cliente cliente = this.dadosCliente.toModel();
		Pedido pedido = new Pedido(cliente);

		return pedido;
	}

	@NoArgsConstructor
	public class ClienteForm {

		@Email
		public String email;

		@NotBlank
		public String nome;

		@NotBlank
		public String sobrenome;

		@CPForCNPJ
		public String documento;

		@NotBlank
		public String cep;

		@NotBlank
		public String endereco;

		@NotBlank
		public String complemento;

		@NotBlank
		public String cidade;

		@NotNull
		@ExistsCountry
		public Long paisId;

		public Long estadoId;

		@NotBlank
		public String telefone;

		@Transactional
		public Cliente toModel() {
			Pais pais = Pais.findById(paisId);
			Optional<Estado> estado = Estado.findByIdOptional(estadoId);

			Cliente client = new Cliente(this.email, this.nome, this.sobrenome, this.documento, this.telefone, this.cep,
					this.endereco, this.complemento, this.cidade, pais, estado);

			return client;
		}

	}

}
