package io.tacsio.order;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.tacsio.order.coupon.Cupom;
import lombok.Getter;

@Getter
@Entity
public class Pedido extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "cliente_id", nullable = false, updatable = false)
	@NotNull
	private Cliente cliente;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "pedido_id", nullable = false)
	private List<ItemPedido> itensPedido;

	@Positive
	private Double total;

	@Positive
	private Double valorFinal;

	@Enumerated(EnumType.STRING)
	private Status status = Status.NAO_INICIADO;

	@OneToOne(optional = true, cascade = { CascadeType.MERGE })
	@JoinColumn(name = "cupom_id", nullable = true)
	private Cupom cupomAplicado;

	@Deprecated
	protected Pedido() {
	}

	public Pedido(@NotNull Cliente cliente, List<ItemPedido> itensPedido, @Positive Double total) {
		this.cliente = cliente;
		this.itensPedido = itensPedido;
		this.total = total;

		this.status = Status.INICIADO;

		if (!total.equals(this.getTotalFromItens())) {
			throw new IllegalArgumentException("Order.total.invalid");
		}
	}

	public Double getTotalFromItens() {
		return itensPedido.stream().mapToDouble(ItemPedido::valorItem).sum();
	}

	public void aplicarCupom(Cupom cupom) {
		if (cupom.expirado()) {
			throw new IllegalArgumentException("Coupon.validity.expired");
		}

		if (existeCupomAplicado()) {
			throw new IllegalArgumentException("Order.coupon.alreadyApplyed");
		}

		this.cupomAplicado = cupom;
		this.aplicarDesconto(cupom.getDesconto());
	}

	private void aplicarDesconto(Double desconto) {
		this.valorFinal = total - total * (desconto / 100);
	}

	public boolean existeCupomAplicado() {
		return this.cupomAplicado != null;
	}

}