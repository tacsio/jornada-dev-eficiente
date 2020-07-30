package io.tacsio.state;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.common.constraint.NotNull;
import io.tacsio.country.Pais;
import lombok.Getter;

@Entity
@Getter
public class Estado extends PanacheEntity {

	@NotBlank
	private String nome;

	@ManyToOne
	@JoinColumn(name = "pais_id", nullable = false)
	@NotNull
	private Pais pais;

	protected Estado() {
	}

	public Estado(@NotBlank String nome, Pais pais) {
		this.nome = nome;
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Estado [nome=" + nome + ", país=" + pais + "]";
	}

}