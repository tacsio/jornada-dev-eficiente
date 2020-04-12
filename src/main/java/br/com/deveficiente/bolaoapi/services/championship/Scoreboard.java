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

    public Integer evaluate(Scoreboard scoreboard) {
        if (bullsEye(scoreboard)) return 10;
        if (someScoreRight(scoreboard) && sameWinner(scoreboard)) return 7;
        if (sameWinner(scoreboard)) return 5;
        if (someScoreRight(scoreboard)) return 2;
        return 0;
    }

    private boolean someScoreRight(Scoreboard scoreboard) {
        return sameHomeGoals(scoreboard) || sameVisitingGoals(scoreboard);
    }

    private boolean bullsEye(Scoreboard other) {
        return sameHomeGoals(other) && sameVisitingGoals(other);
    }

    private boolean sameHomeGoals(Scoreboard other) {
        return this.homeTeamGoals.equals(other.getHomeTeamGoals());
    }


    private boolean sameVisitingGoals(Scoreboard other) {
        return this.visitingTeamGoals.equals(other.getVisitingTeamGoals());
    }

    private boolean sameWinner(Scoreboard other) {
        return this.winner() == other.winner();
    }

    /**
     * Returns
     * 1 if home team win
     * -1 if visit team win
     * 0 if draw
     *
     * @return
     */
    private int winner() {
        return this.homeTeamGoals.compareTo(visitingTeamGoals);
    }

}
