package br.com.deveficiente.bolaoapi.services.championship.api.model;

import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.championship.Scoreboard;
import br.com.deveficiente.bolaoapi.services.championship.Shot;
import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@ToString
public class CreateShotRequest {

    @Getter
    @PositiveOrZero
    private Integer homeTeamGoals;

    @Getter
    @PositiveOrZero
    private Integer visitingTeamGoals;

    @Getter
    @NotNull
    private Boolean doubled;

    public Shot toShot(User participant, Match match) {
        Scoreboard scoreboard = new Scoreboard(homeTeamGoals, visitingTeamGoals);
        Shot shot = new Shot(participant, match, scoreboard, doubled);

        return shot;
    }
}


