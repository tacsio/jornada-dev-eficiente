package br.com.deveficiente.bolaoapi.services.championship.api.model;

import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.championship.MatchResult;
import br.com.deveficiente.bolaoapi.services.championship.Scoreboard;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.PositiveOrZero;

@ToString
public class CreateMatchResultRequest {

    @Getter
    @PositiveOrZero
    private Integer homeTeamGoals;

    @Getter
    @PositiveOrZero
    private Integer visitingTeamGoals;

    public CreateMatchResultRequest(@PositiveOrZero Integer homeTeamGoals, @PositiveOrZero Integer visitingTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
        this.visitingTeamGoals = visitingTeamGoals;
    }

    public MatchResult toMatchResult(Match match) {
        Scoreboard scoreboard = new Scoreboard(homeTeamGoals, visitingTeamGoals);
        MatchResult matchResult = new MatchResult(match, scoreboard);

        return matchResult;
    }
}
