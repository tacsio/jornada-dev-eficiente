package io.tacsio.category.dto;

import io.tacsio.category.Categoria;
import io.tacsio.category.validator.UniqueCategory;
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
