package io.tacsio.coupon.dto;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;

import io.tacsio.coupon.Cupom;
import lombok.ToString;

@ToString
public class CupomResponse {

	public final String codigo;

	public final Double desconto;

	@JsonbDateFormat("dd-MM-yyyy hh:mm")
	public final LocalDateTime validade;

	public CupomResponse(Cupom cupom) {
		this.codigo = cupom.getCodigo();
		this.desconto = cupom.getDesconto();
		this.validade = cupom.getValidade();
	}

}