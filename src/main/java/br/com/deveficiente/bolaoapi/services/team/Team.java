package br.com.deveficiente.bolaoapi.services.team;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "uk_team_name"))
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotBlank
    @Getter
    private String name;

    @NotNull
    @Past
    @Getter
    private LocalDate foundation;

    public Team() {
    }

    public Team(String name, LocalDate foundation) {
        this.name = name;
        this.foundation = foundation;
    }
}
