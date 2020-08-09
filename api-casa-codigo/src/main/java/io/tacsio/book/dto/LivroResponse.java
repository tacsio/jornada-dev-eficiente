package io.tacsio.book.dto;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;

import io.tacsio.author.dto.AutorResponse;
import io.tacsio.book.Livro;
import io.tacsio.book.category.dto.CategoriaResponse;
import lombok.ToString;

@ToString
public class LivroResponse {

	public final long id;

	public final String titulo;

	public final String resumo;

	public final String sumario;

	public final double preco;

	public final int paginas;

	public final String isbn;

	@JsonbDateFormat("dd-MM-yyyy")
	public final LocalDate dataPublicacao;

	public final CategoriaResponse categoria;

	public final AutorResponse autor;

	public LivroResponse(Livro livro) {
		this.id = livro.getId();
		this.titulo = livro.getTitulo();
		this.resumo = livro.getResumo();
		this.sumario = livro.getSumario();
		this.preco = livro.getPreco();
		this.paginas = livro.getPaginas();
		this.isbn = livro.getIsbn();
		this.dataPublicacao = livro.getDataPublicacao();
		this.categoria = new CategoriaResponse(livro.getCategoria());
		this.autor = new AutorResponse(livro.getAutor());
	}
}
