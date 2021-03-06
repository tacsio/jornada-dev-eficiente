package br.com.deveficiente.bolaoapi.services.championship.api.model.championship;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.team.Team;
import br.com.deveficiente.bolaoapi.services.team.TeamRepository;
import br.com.deveficiente.bolaoapi.shared.validator.ChampionshipTeamsRequest;
import br.com.deveficiente.bolaoapi.shared.validator.DatabaseTeams;
import br.com.deveficiente.bolaoapi.shared.validator.Unique;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Data
@ChampionshipTeamsRequest
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

    @DatabaseTeams
    @Getter
    private Set<Long> teamsId = new HashSet<>();

    public Championship toChampionship(TeamRepository teamRepository) {
        Set<Team> mergedTeams = this.teamsId.stream()
                .map(id -> teamRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        return new Championship(this.name, this.startDate, this.totalTeams, mergedTeams);
    }
}
