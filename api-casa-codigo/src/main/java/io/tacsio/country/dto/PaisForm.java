package io.tacsio.country.dto;

import javax.validation.constraints.NotBlank;

import io.tacsio.country.Pais;
import io.tacsio.country.validator.UniqueCountry;

public class PaisForm {

	@NotBlank
	@UniqueCountry
	public String nome;

	public Pais toModel() {
		return new Pais(nome);
	}
}