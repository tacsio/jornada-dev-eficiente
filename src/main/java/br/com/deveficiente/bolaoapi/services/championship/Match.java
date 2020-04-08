package br.com.deveficiente.bolaoapi.services.championship;

import br.com.deveficiente.bolaoapi.services.team.Team;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;
import java.util.*;

import static org.hibernate.annotations.CascadeType.*;

@ToString(exclude = {"championship", "shots"})
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

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "match")
    @Cascade(value = {MERGE, PERSIST, REFRESH})
    private Set<Shot> shots = new HashSet<>();


    protected Match() {
    }

    public Match(@NotNull Championship championship, @Positive Integer round, @NotNull Team homeTeam, @NotNull Team visitingTeam, @Future LocalTime startTime) {
        this.championship = championship;
        this.round = round;
        this.homeTeam = homeTeam;
        this.visitingTeam = visitingTeam;
        this.startTime = startTime;
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

    public void addShot(@Valid Shot shot) {
        Assert.isTrue(!shots.contains(shot), "Shot already made for this match.");
        Assert.isTrue(!shot.getDoubled() || !hasDoubledShotInRound(shot), "Only one doubled shot is permitted per round.");

        this.shots.add(shot);
    }

    private boolean hasDoubledShotInRound(Shot newDoubledShot) {
        Optional<Shot> doubledShot = newDoubledShot.getMatch().getChampionship().championshipMatches()
                .shotsByRound(newDoubledShot.getMatch().getRound())
                .filter(s -> Objects.equals(s.getParticipant(), newDoubledShot.getParticipant()))
                .filter(Shot::getDoubled)
                .findAny();

        return doubledShot.isPresent();
    }

    @Override
    public int hashCode() {
        return Objects.hash(championship, round, homeTeam, visitingTeam);
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

}
