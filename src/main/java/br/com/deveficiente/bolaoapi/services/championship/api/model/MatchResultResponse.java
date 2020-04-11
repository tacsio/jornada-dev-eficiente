package br.com.deveficiente.bolaoapi.services.championship.api.model;

import br.com.deveficiente.bolaoapi.services.championship.MatchResult;
import lombok.Getter;
import lombok.ToString;

@ToString
public class MatchResultResponse {

    @Getter
    private String match;
    @Getter
    private Integer homeTeamGoals;
    @Getter
    private Integer visitingTeamGoals;

    public MatchResultResponse(MatchResult matchResult) {
        match = matchResult.getMatch().getDescription();
        homeTeamGoals = matchResult.getScoreboard().getHomeTeamGoals();
        visitingTeamGoals = matchResult.getScoreboard().getVisitingTeamGoals();
    }
}
