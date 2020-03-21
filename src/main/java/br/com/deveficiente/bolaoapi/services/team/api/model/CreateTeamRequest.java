package br.com.deveficiente.bolaoapi.services.team.api.model;

import br.com.deveficiente.bolaoapi.services.team.Team;
import br.com.deveficiente.bolaoapi.shared.validator.Unique;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Data
public class CreateTeamRequest {

    @NotBlank(message = "Team name should not be null.")
    @Unique(entityClass = Team.class)
    private String name;

    @NotNull(message = "Date of foundation should not be null.")
    @Past(message = "Date of foundation not allowed. It must be a past date.")
    @DateTimeFormat(iso = DATE)
    private LocalDate foundation;

    public Team getTeam() {
        return new Team(this.name, this.foundation);
    }
}
