package br.com.deveficiente.bolaoapi.shared.validator;

import br.com.deveficiente.bolaoapi.services.championship.api.model.CreateChampionshipRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChampionshipTeamsRequestValidator implements ConstraintValidator<ChampionshipTeamsRequest, CreateChampionshipRequest> {

    @Override
    public boolean isValid(CreateChampionshipRequest value, ConstraintValidatorContext context) {
        return value.getTeamsId().size() == value.getTotalTeams();
    }
}
