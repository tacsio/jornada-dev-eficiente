package br.com.deveficiente.bolaoapi.services.poll;

import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.championship.Scoreboard;
import lombok.Getter;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"participant_id", "match_id"}, name = "uk_match_shot"))
public class Shot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @NotNull
    @ManyToOne
    private Participant participant;

    @Getter
    @NotNull
    @ManyToOne
    private Match match;

    @Getter
    @NotNull
    @Embedded
    private Scoreboard scoreboard;

    @Getter
    @NotNull
    private Boolean doubled;

    protected Shot() {
    }

    public Shot(@NotNull Participant participant, @NotNull Match match, @NotNull Scoreboard scoreboard, @NotNull Boolean doubled) {
        this.participant = participant;
        this.match = match;
        this.scoreboard = scoreboard;
        this.doubled = doubled;

        validateShot();
    }

    @PostConstruct
    public void validateShot() {
        Assert.isTrue(!participant.getShots().contains(this), "Shot already made for this match.");
        Assert.isTrue(!this.doubled || !this.participant.hasDoubledShotInRound(this.match.getRound()), "Only one doubled shot is permitted per round.");
    }

    public Integer processShotScore() {
        int multiplier = doubled ? 2 : 1;
        return this.scoreboard.evaluate(this.match.getResult().getScoreboard()) * multiplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shot shot = (Shot) o;
        return participant.equals(shot.participant) &&
                match.equals(shot.match);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participant, match);
    }
}
