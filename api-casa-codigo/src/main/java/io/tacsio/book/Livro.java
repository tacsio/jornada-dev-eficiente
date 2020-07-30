package io.tacsio.book;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.tacsio.author.Autor;
import io.tacsio.category.Categoria;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Entity
@ToString
public class Livro extends PanacheEntity {

	@NotBlank
	private String titulo;

	@NotBlank
	@Column(length = 500)
	private String resumo;

	private String sumario;

	@Min(20)
	private double preco;

	@Min(100)
	private int paginas;

	@NotBlank
	private String isbn;

	@Future
	private LocalDate dataPublicacao;

	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "autor_id", nullable = false)
	private Autor autor;

	protected Livro() {
	}

	public Livro(@NotBlank String titulo, @NotBlank String resumo, String sumario, @Size(min = 20) double preco, @Size(min = 100) int paginas, @NotBlank String isbn, @Future LocalDate dataPublicacao, Categoria categoria, Autor autor) {
		this.titulo = titulo;
		this.resumo = resumo;
		this.sumario = sumario;
		this.preco = preco;
		this.paginas = paginas;
		this.isbn = isbn;
		this.dataPublicacao = dataPublicacao;
		this.categoria = categoria;
		this.autor = autor;
	}
}
