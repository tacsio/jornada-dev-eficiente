package io.tacsio.order;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;

@Getter
@Entity
public class Pedido extends PanacheEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "client_id", nullable = false, updatable = false)
	@NotNull
	private Cliente cliente;

	@Deprecated
	protected Pedido() {
	}

	public Pedido(@NotNull Cliente cliente) {
		this.cliente = cliente;
	}

	

}