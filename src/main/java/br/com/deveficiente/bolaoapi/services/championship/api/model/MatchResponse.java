package br.com.deveficiente.bolaoapi.services.championship.api.model;

import br.com.deveficiente.bolaoapi.services.championship.Match;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@ToString
public class MatchResponse {

    @Getter
    private Long id;

    @Getter
    private String homeTeam;

    @Getter
    private String visitingTeam;

    @Getter
    private String championship;

    @Getter
    private Integer round;

    @Getter
    private LocalTime startTime;

    public MatchResponse(Match match) {
        this.id = match.getId();
        this.homeTeam = match.getHomeTeam().getName();
        this.visitingTeam = match.getVisitingTeam().getName();
        this.championship = match.getChampionship().getName();
        this.round = match.getRound();
        this.startTime = match.getStartTime();
    }
}
