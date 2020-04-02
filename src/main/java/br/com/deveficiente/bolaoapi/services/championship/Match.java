package br.com.deveficiente.bolaoapi.services.championship;

import br.com.deveficiente.bolaoapi.services.team.Team;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;
import java.util.Objects;

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
    @Column(columnDefinition = "TIME")
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

    @Override
    public int hashCode() {
        return Objects.hash(championship, round, homeTeam, visitingTeam);
    }
}
