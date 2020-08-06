package io.tacsio.category.dto;

import io.tacsio.category.Categoria;
import lombok.Getter;

@Getter
public class CategoriaResponse {
	private final long id;
	private final String nome;

	public CategoriaResponse(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
	}
}
