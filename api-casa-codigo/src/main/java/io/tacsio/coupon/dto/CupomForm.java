package io.tacsio.coupon.dto;

import java.time.LocalDateTime;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.tacsio.coupon.Cupom;
import io.tacsio.coupon.validator.UniqueCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class CupomForm {

	@UniqueCode
	@NotEmpty
	public String codigo;

	@Positive
	public Double desconto;

	@Future
	@JsonbDateFormat("dd-MM-yyyy HH:mm")
	public LocalDateTime validade;

	public Cupom toModel() {
		return new Cupom(codigo, desconto, validade);
	}

}