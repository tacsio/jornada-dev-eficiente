package br.com.deveficiente.bolaoapi.services.championship.api.model;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.team.Team;
import br.com.deveficiente.bolaoapi.services.team.TeamRepository;
import br.com.deveficiente.bolaoapi.services.team.api.model.CreateTeamRequest;
import br.com.deveficiente.bolaoapi.shared.validator.Unique;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Data
public class CreateChampionshipRequest {

    @Getter
    @NotBlank(message = "Championship name should not be null.")
    @Unique(entityClass = Championship.class, entityField = "name")
    private String name;

    @Getter
    @DateTimeFormat(iso = DATE)
    @FutureOrPresent
    private LocalDate startDate;

    @Getter
    @Min(2)
    private Integer totalTeams;

    @Getter
    private Set<CreateTeamRequest> teams = new HashSet<>();

    public Championship getChampionship(TeamRepository teamRepository) {
        Set<Team> mergedTeams = this.teams.stream()
                .map(requestTeam -> teamRepository.findByName(requestTeam.getName())
                .orElseGet(() -> requestTeam.getTeam()))
                .collect(Collectors.toSet());

        return new Championship(this.name, this.startDate, this.totalTeams, mergedTeams);
    }
}
