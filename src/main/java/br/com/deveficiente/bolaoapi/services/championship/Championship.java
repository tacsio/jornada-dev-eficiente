package br.com.deveficiente.bolaoapi.services.championship;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "uk_championship_name"))
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @NotBlank
    private String name;

    @Getter
    @FutureOrPresent
    private LocalDate startDate;

    @Getter
    private Integer totalTeams;


    public Championship() {
    }

    public Championship(String name, LocalDate startDate, Integer totalTeams) {
        this.name = name;
        this.startDate = startDate;
        this.totalTeams = totalTeams;
    }
}
