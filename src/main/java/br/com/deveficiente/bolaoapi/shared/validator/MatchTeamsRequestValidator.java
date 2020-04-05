package br.com.deveficiente.bolaoapi.shared.validator;


import br.com.deveficiente.bolaoapi.services.championship.api.model.CreateMatchRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchTeamsRequestValidator implements ConstraintValidator<MatchTeamsRequest, CreateMatchRequest> {

    @Override
    public boolean isValid(CreateMatchRequest request, ConstraintValidatorContext context) {
        return request.validMatchTeams();
    }
}
