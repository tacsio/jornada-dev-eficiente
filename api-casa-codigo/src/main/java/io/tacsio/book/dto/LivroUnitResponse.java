package io.tacsio.book.dto;

import io.tacsio.book.Livro;

public class LivroUnitResponse {
	public final long id;
	public final String titulo;

	public LivroUnitResponse(Livro livro) {
		this.id = livro.id;
		this.titulo = livro.getTitulo();
	}

}