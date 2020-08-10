package io.tacsio.order.coupon;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Cupom extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(unique = true)
	private String codigo;

	@Positive
	private Double desconto;

	@Future
	private LocalDateTime validade;

	@Deprecated
	protected Cupom() {
	}

	public Cupom(@NotEmpty String codigo, @Positive Double desconto, @Future LocalDateTime validade) {
		this.codigo = codigo;
		this.desconto = desconto;
		this.validade = validade;
	}

	public boolean expirado() {
		return this.validade.isBefore(LocalDateTime.now());
	}

}