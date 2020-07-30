package io.tacsio.country;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Pais extends PanacheEntity {

	@NotEmpty
	private String nome;

	protected Pais() {
	}

	public Pais(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "País [nome=" + nome + "]";
	}

}