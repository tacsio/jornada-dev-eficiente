package io.tacsio.country;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;

@Entity
@Getter
public class Pais extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nome;

	protected Pais() {
	}

	public Pais(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "País [nome=" + nome + "]";
	}

}