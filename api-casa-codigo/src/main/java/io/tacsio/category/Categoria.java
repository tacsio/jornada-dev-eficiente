package io.tacsio.category;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
public class Categoria extends PanacheEntity {

	@NotBlank
	@Column(unique = true)
	private String nome;


	protected Categoria() {
	}

	public Categoria(String nome) {
		this.nome = nome;
	}
}
