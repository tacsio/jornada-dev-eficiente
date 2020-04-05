package br.com.deveficiente.bolaoapi.services.championship;

import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
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
}
