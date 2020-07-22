package io.tacsio.author.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tacsio.author.Autor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AutorResponse {
	private String nome;
	private String email;
	private String descricao;
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime createdAt;

	public AutorResponse(Autor autor) {
		this.nome = autor.getNome();
		this.email = autor.getEmail();
		this.descricao = autor.getDescricao();
		this.createdAt = autor.getCreatedAt();
	}
}