package io.tacsio.order;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.tacsio.country.Pais;
import io.tacsio.order.validator.CPForCNPJ;
import io.tacsio.state.Estado;
import lombok.Getter;

@Entity
@Getter
public class Cliente extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Email
	private String email;

	@NotBlank
	private String nome;

	@NotBlank
	private String sobrenome;

	@CPForCNPJ
	private String documento;

	@NotBlank
	private String telefone;

	@NotBlank
	private String cep;

	@NotBlank
	private String endereco;

	@NotBlank
	private String complemento;

	@NotBlank
	private String cidade;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "pais_id", nullable = false)
	private Pais pais;

	@ManyToOne(optional = true)
	@JoinColumn(name = "estado_id", nullable = true)
	private Estado estado;

	@Deprecated
	protected Cliente() {
	}

	public Cliente(@Email String email, @NotBlank String nome, @NotBlank String sobrenome, @CPForCNPJ String documento,
			@NotBlank String telefone, @NotBlank String cep, @NotBlank String endereco, @NotBlank String complemento,
			@NotBlank String cidade, @NotNull Pais pais, Optional<Estado> estado) {
		this.email = email;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.documento = documento;
		this.cep = cep;
		this.endereco = endereco;
		this.complemento = complemento;
		this.cidade = cidade;
		this.pais = pais;
		this.estado = estado.orElse(null);
		this.telefone = telefone;
	}

}