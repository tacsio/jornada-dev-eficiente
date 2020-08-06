package io.tacsio.country.dto;

import io.tacsio.country.Pais;
import lombok.Getter;

@Getter
public class PaisResponse {

	private Long id;

	private String nome;

	public PaisResponse(Pais pais) {
		this.id = pais.getId();
		this.nome = pais.getNome();
	}

	@Override
	public String toString() {
		return "PaísResponse [nome=" + nome + "]";
	}

}