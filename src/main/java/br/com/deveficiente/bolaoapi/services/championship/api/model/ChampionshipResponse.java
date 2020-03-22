package br.com.deveficiente.bolaoapi.services.championship.api.model;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ToString
public class ChampionshipResponse {

    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private LocalDate startDate;

    @Getter
    private Integer totalTeams;


    public ChampionshipResponse(@Valid @NotNull Championship championship) {
        this.id = championship.getId();
        this.name = championship.getName();
        this.startDate = championship.getStartDate();
        this.totalTeams = championship.getTotalTeams();
    }
}
