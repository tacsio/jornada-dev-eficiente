package io.tacsio.book.dto;

import java.time.LocalDate;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.tacsio.author.Autor;
import io.tacsio.book.Livro;
import io.tacsio.book.validator.UniqueBookTitle;
import io.tacsio.category.Categoria;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class LivroForm {

	@NotBlank
	@UniqueBookTitle
	public String titulo;

	@NotBlank
	public String resumo;

	public String sumario;

	@Min(20)
	public Double preco;

	@Min(100)
	public Integer paginas;

	@NotBlank
	public String isbn;

	@Future
	@JsonbDateFormat("dd-MM-yyyy")
	public LocalDate dataPublicacao;

	@NonNull
	public Long idCategoria;

	@NonNull
	public Long idAutor;

	public Livro toModel() {
		Autor autor = Autor.findById(idAutor);
		Categoria categoria = Categoria.findById(idCategoria);

		return new Livro(titulo, resumo, sumario, preco, paginas, isbn, dataPublicacao, categoria, autor);
	}
}
