package br.com.deveficiente.bolaoapi.services.championship;

import br.com.deveficiente.bolaoapi.services.team.Team;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.*;

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
    @Min(2)
    private Integer totalTeams;

    @Getter
    @ManyToMany
    @Cascade(value = {MERGE, PERSIST, REFRESH})
    private Set<Team> teams = new HashSet<>();

    public Championship() {
    }

    public Championship(String name, LocalDate startDate, Integer totalTeams, Set<Team> teams) {
        Assert.isTrue(totalTeams == teams.size(), "invalid number of teams.");

        this.name = name;
        this.startDate = startDate;
        this.totalTeams = totalTeams;
        this.teams.addAll(teams);
    }
}