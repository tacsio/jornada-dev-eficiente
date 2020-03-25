package br.com.deveficiente.bolaoapi.shared.validator;

import br.com.deveficiente.bolaoapi.services.team.TeamRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.Set;

public class ChampionshipTeamsValidator implements ConstraintValidator<ChampionshipTeams, Set<Long>> {

    private TeamRepository teamRepository;

    public ChampionshipTeamsValidator(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public boolean isValid(Set<Long> ids, ConstraintValidatorContext context) {
        long requestCount = ids.size();

        long databaseCount = ids.stream()
                .map(id -> teamRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .count();

        return requestCount == databaseCount;
    }
}
