package io.tacsio.book.category.dto;

import io.tacsio.book.category.Categoria;
import io.tacsio.book.category.validator.UniqueCategory;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
public class CategoriaForm {
	@NotEmpty
	@UniqueCategory
	public String nome;

	public CategoriaForm(@NotEmpty String nome) {
		this.nome = nome;
	}

	public Categoria toModel() {
		return new Categoria(this.nome);
	}
}
