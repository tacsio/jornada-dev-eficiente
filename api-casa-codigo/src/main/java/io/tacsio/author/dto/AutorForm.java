package io.tacsio.author.dto;

import io.tacsio.author.Autor;
import io.tacsio.author.validator.UniqueAuthorEmail;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
public class AutorForm {
	@NotEmpty
	public String nome;
	@Email
	@UniqueAuthorEmail
	public String email;
	@NotEmpty
	@Size(max = 400)
	public String descricao;

	public AutorForm(@NotEmpty String nome, @Email String email, @NotEmpty @Size(max = 400) String descricao) {
		this.nome = nome;
		this.email = email;
		this.descricao = descricao;
	}

	public Autor toModel() {
		return new Autor(this.nome, this.email, this.descricao);
	}
}
