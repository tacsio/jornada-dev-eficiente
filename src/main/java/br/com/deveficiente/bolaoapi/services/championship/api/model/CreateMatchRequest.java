package br.com.deveficiente.bolaoapi.services.championship.api.model;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.team.Team;
import br.com.deveficiente.bolaoapi.services.team.TeamRepository;
import br.com.deveficiente.bolaoapi.shared.validator.Exists;
import br.com.deveficiente.bolaoapi.shared.validator.MatchTeamsRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;

@ToString
@MatchTeamsRequest
public class CreateMatchRequest {

    @NotNull
    @Positive
    private Integer round;

    @Getter
    @NotNull
    @Exists(entityClass = Team.class)
    private Long homeTeamId;

    @Getter
    @NotNull
    @Exists(entityClass = Team.class)
    private Long visitingTeamId;

    @Getter
    @NotNull
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    public CreateMatchRequest(@NotNull Long homeTeamId, @NotNull Long visitingTeamId, @NotNull @Future LocalTime startTime, @NotNull @Positive Integer round) {
        this.homeTeamId = homeTeamId;
        this.visitingTeamId = visitingTeamId;
        this.startTime = startTime;
        this.round = round;
    }

    @Valid
    public Match toMatch(TeamRepository teamRepository, Championship championship) {
        Team homeTeam = teamRepository.findById(this.homeTeamId).get();
        Team visitingTeam = teamRepository.findById(this.visitingTeamId).get();

        return new Match(championship, round, homeTeam, visitingTeam, startTime);
    }
}
