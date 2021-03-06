package io.tacsio.country.state;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.tacsio.country.Pais;
import lombok.Getter;

@Entity
@Getter
public class Estado extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
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
	
	public boolean of(@NotNull Pais pais) {
		return this.pais.getId() == pais.getId();
	}

	@Override
	public String toString() {
		return "Estado [nome=" + nome + ", país=" + pais + "]";
	}


}