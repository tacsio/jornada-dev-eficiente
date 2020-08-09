package io.tacsio.book.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;

@Entity
@Getter
public class Categoria extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String nome;


	protected Categoria() {
	}

	public Categoria(String nome) {
		this.nome = nome;
	}
}
