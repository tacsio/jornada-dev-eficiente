package br.com.deveficiente.bolaoapi.services.championship;

import br.com.deveficiente.bolaoapi.services.team.Team;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

@ToString(exclude = "championship")
@Entity
public class Match {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotNull
    @ManyToOne
    private Championship championship;

    @Getter
    @Positive
    private Integer round;

    @Getter
    @NotNull
    @ManyToOne
    private Team homeTeam;

    @Getter
    @NotNull
    @ManyToOne
    private Team visitingTeam;

    @Getter
    @NotNull
    @Future
    @Column
    private LocalTime startTime;


    protected Match() {
    }

    public Match(@NotNull Championship championship, @Positive Integer round, @NotNull Team homeTeam, @NotNull Team visitingTeam, @Future LocalTime startTime) {
        this.championship = championship;
        this.round = round;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return championship.equals(match.championship) &&
                round.equals(match.round) &&
                homeTeam.equals(match.homeTeam) &&
                visitingTeam.equals(match.visitingTeam);
    }

    public boolean hasConflict(Match other) {
        Set<Team> matchTeams = getTeamsOfMatch();
        return this.round == other.getRound() && (
                matchTeams.contains(other.getHomeTeam()) ||
                matchTeams.contains(other.getVisitingTeam())
        );
    }

    private Set<Team> getTeamsOfMatch() {
        Set<Team> matchTeams = new HashSet<>();
        matchTeams.add(homeTeam);
        matchTeams.add(visitingTeam);

        return matchTeams;
    }

    private Predicate<Match> hasTeam(Team team) {
        return match -> (match.getVisitingTeam().equals(team) || match.getHomeTeam().equals(team));
    }

    @Override
    public int hashCode() {
        return Objects.hash(championship, round, homeTeam, visitingTeam);
    }
}
