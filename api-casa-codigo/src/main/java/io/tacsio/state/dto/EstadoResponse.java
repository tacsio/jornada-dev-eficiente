package io.tacsio.state.dto;

import io.tacsio.country.dto.PaisResponse;
import io.tacsio.state.Estado;
import lombok.Getter;

@Getter
public class EstadoResponse {

	private Long id;

	private String nome;

	private PaisResponse pais;

	public EstadoResponse(Estado estado) {
		this.id = estado.id;
		this.nome = estado.getNome();
		this.pais = new PaisResponse(estado.getPais());
	}

	@Override
	public String toString() {
		return "EstadoResponse [nome=" + nome + ", país=" + pais + "]";
	}

}
