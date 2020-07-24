package io.tacsio.category.dto;

import io.tacsio.category.Categoria;
import lombok.Getter;

@Getter
public class CategoriaResponse {
	private String nome;

	public CategoriaResponse(Categoria categoria) {
		this.nome = categoria.getNome();
	}
}
