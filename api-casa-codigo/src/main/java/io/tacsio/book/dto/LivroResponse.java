package io.tacsio.book.dto;

import io.tacsio.author.dto.AutorResponse;
import io.tacsio.book.Livro;
import io.tacsio.category.dto.CategoriaResponse;
import lombok.Getter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;

@ToString
@Getter
public class LivroResponse {

	private final long id;

	private final String titulo;

	private final String resumo;

	private final String sumario;

	private final double preco;

	private final int paginas;

	private final String isbn;

	@JsonbDateFormat("dd-MM-yyyy")
	private final LocalDate dataPublicacao;

	private final CategoriaResponse categoria;

	private final AutorResponse autor;

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
