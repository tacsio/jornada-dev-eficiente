package io.tacsio.state.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import io.smallrye.common.constraint.NotNull;
import io.tacsio.country.Pais;
import io.tacsio.country.validator.ExistsCountry;
import io.tacsio.state.Estado;
import io.tacsio.state.validator.UniqueState;

public class EstadoForm {

	@NotBlank
	@UniqueState
	public String nome;

	@NotNull
	@ExistsCountry
	@Positive
	public Long paisId;

	public Estado toModel() {
		Pais pais = Pais.findById(paisId);

		return new Estado(nome, pais);
	}

}
