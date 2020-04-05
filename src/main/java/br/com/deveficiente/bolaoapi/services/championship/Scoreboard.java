package br.com.deveficiente.bolaoapi.services.championship;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.validation.constraints.PositiveOrZero;

@Embeddable
public class Scoreboard {

    @Getter
    @PositiveOrZero
    private Integer homeTeamGoals;

    @Getter
    @PositiveOrZero
    private Integer visitingTeamGoals;

    protected Scoreboard() {
    }

    public Scoreboard(@PositiveOrZero Integer homeTeamGoals, @PositiveOrZero Integer visitingTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
        this.visitingTeamGoals = visitingTeamGoals;
    }
}
