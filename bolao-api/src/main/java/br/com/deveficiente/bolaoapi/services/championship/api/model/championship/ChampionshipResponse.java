package br.com.deveficiente.bolaoapi.services.championship.api.model.championship;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.team.api.model.TeamResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
public class ChampionshipResponse {

    @Getter
    private final Long id;

    @Getter
    private final String name;

    @Getter
    private final LocalDate startDate;

    @Getter
    private final Integer totalTeams;

    @Getter
    @JsonIgnoreProperties
    private final Set<TeamResponse> teams;


    public ChampionshipResponse(@Valid @NotNull Championship championship) {
        this.id = championship.getId();
        this.name = championship.getName();
        this.startDate = championship.getStartDate();
        this.totalTeams = championship.getTotalTeams();

        this.teams = championship.getTeams()
                .stream()
                .map(TeamResponse::new)
                .collect(Collectors.toSet());
    }
}
