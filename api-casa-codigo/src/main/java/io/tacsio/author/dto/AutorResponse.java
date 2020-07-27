package io.tacsio.author.dto;

import io.tacsio.author.Autor;
import lombok.Getter;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDateTime;

@Getter
public class AutorResponse {
	private final long id;
	private final String nome;
	private final String email;
	private final String descricao;
	@JsonbDateFormat("dd-MM-yyyy HH:mm:ss")
	private final LocalDateTime createdAt;

	public AutorResponse(Autor autor) {
		this.id = autor.id;
		this.nome = autor.getNome();
		this.email = autor.getEmail();
		this.descricao = autor.getDescricao();
		this.createdAt = autor.getCreatedAt();
	}
}