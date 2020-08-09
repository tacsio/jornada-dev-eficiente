package io.tacsio.order.validator;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.country.Pais;
import io.tacsio.country.state.Estado;
import io.tacsio.order.dto.ClienteForm;

@ApplicationScoped
public class ClientCountryStateValidator implements ConstraintValidator<ClientCountryState, ClienteForm> {

	@Override
	public boolean isValid(ClienteForm form, ConstraintValidatorContext context) {
		Pais pais = Pais.findById(form.paisId);
		if (pais == null)
			return false;

		return validFormState(pais, form.estadoId);
	}

	public boolean validFormState(Pais pais, Long estadoId) {
		boolean temEstados = !pais.getEstados().isEmpty();
		boolean temIdEstadoForm = estadoId != null;

		// Valido se estados e idForm existe ou não (coincidência !XOR)
		boolean valid = temEstados == temIdEstadoForm;

		// valida se o id do form pertence ao pais
		if (valid && temIdEstadoForm) {
			Estado estado = Estado.findById(estadoId);
			valid &= estado != null && estado.of(pais);
		}

		return valid;
	}
}
