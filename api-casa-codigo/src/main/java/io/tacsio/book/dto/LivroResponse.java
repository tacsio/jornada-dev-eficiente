package io.tacsio.book.dto;

import io.tacsio.author.dto.AutorResponse;
import io.tacsio.book.Livro;
import io.tacsio.category.dto.CategoriaResponse;
import lombok.Getter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;

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
		this.id = livro.id;
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
