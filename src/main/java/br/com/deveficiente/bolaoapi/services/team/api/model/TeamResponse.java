package br.com.deveficiente.bolaoapi.services.team.api.model;

import br.com.deveficiente.bolaoapi.services.team.Team;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ToString
public class TeamResponse {

    @Getter
    private final Long id;
    @Getter
    private final String name;
    @Getter
    private final LocalDate foundation;

    public TeamResponse(@Valid @NotNull Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.foundation = team.getFoundation();
    }
}
