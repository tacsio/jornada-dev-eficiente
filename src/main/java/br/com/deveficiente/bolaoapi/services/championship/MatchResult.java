package br.com.deveficiente.bolaoapi.services.championship;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@ToString(exclude = "match")
@Entity
public class MatchResult {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @OneToOne
    private Match match;

    @Getter
    @NotNull
    @Embedded
    private Scoreboard scoreboard;

    protected MatchResult() {
    }

    public MatchResult(@Valid Match match, @Valid Scoreboard scoreboard) {
        this.match = match;
        this.scoreboard = scoreboard;
    }
}
