package br.com.deveficiente.bolaoapi.services.championship;

import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;

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
    private User participant;

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

    public Shot(@NotNull User participant, @NotNull Match match, @NotNull Scoreboard scoreboard, @NotNull Boolean doubled) {
        this.participant = participant;
        this.match = match;
        this.scoreboard = scoreboard;
        this.doubled = doubled;
    }

    public void update(Shot shot) {
        this.scoreboard = shot.getScoreboard();
        this.doubled = shot.getDoubled();
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
