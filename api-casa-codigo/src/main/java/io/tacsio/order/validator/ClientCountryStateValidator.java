package io.tacsio.order.validator;


import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.tacsio.country.Pais;
import io.tacsio.country.state.Estado;
import io.tacsio.order.dto.ClienteForm;

@ApplicationScoped
public class ClientCountryStateValidator implements ConstraintValidator<ClientCountryState, ClienteForm> {

	@Override
	public boolean isValid(ClienteForm form, ConstraintValidatorContext context) {
		return validFormState(form);
	}

	@Transactional
	public boolean validFormState(ClienteForm form) {
		Pais pais = Pais.findById(form.paisId);

		boolean temEstados = !pais.getEstados().isEmpty();
		boolean temIdEstadoForm = form.estadoId != null;

		// Valido se estados e idForm existe ou não (coincidência !XOR)
		boolean valid = temEstados == temIdEstadoForm;

		// valida se o id do form pertence ao pais
		if (valid && temIdEstadoForm) {
			Estado estado = Estado.findById(form.estadoId);
			valid &= estado != null && estado.of(pais);
		}

		return valid;
	}
}
