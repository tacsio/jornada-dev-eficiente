package io.tacsio.order;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;

@Getter
@Entity
public class Pedido extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
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