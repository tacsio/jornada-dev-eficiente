package io.tacsio.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.tacsio.book.Livro;
import lombok.Getter;

@Entity
@Getter
public class ItemPedido extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "livro_id", nullable = false, updatable = false)
	private Livro livro;

	@Positive
	private Double preco;

	@Positive
	private Integer quantidade;

	@Deprecated
	protected ItemPedido() {
	}

	public ItemPedido(@NotNull Livro livro, @Positive Integer quantidade) {
		this.livro = livro;
		this.preco = livro.getPreco();
		this.quantidade = quantidade;
	}

	public Double valorItem() {
		return this.preco * this.quantidade;
	}

}