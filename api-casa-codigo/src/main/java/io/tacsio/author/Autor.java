package io.tacsio.author;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
public class Autor extends PanacheEntity {

	@NotEmpty
	private String nome;
	@Email
	@Column(unique = true)
	private String email;
	@NotEmpty
	@Column(length = 400)
	private String descricao;
	@NotNull
	@CreationTimestamp
	private LocalDateTime createdAt;

	public Autor(String nome, String email, String descricao) {
		this.nome = nome;
		this.email = email;
		this.descricao = descricao;
		this.createdAt = LocalDateTime.now();
	}

	protected Autor() {
	}
}