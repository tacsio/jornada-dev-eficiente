package br.com.deveficiente.bolaoapi.shared.validator;

import br.com.deveficiente.bolaoapi.services.team.TeamRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class NumberOfTeamsValidator implements ConstraintValidator<NumberOfTeams, Set<Long>> {

    private TeamRepository teamRepository;

    public NumberOfTeamsValidator(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public boolean isValid(Set<Long> ids, ConstraintValidatorContext context) {
        long requestCount = ids.size();
        long databaseCount = teamRepository.findAllById(ids)
                .stream()
                .count();

        return requestCount == databaseCount;
    }
}
