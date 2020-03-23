package br.com.deveficiente.bolaoapi.services.team;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ToString(exclude = "championships")
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

    @ManyToMany(mappedBy = "teams")
    @Getter
    private Set<Championship> championships;

    public Team() {
    }

    public Team(String name, LocalDate foundation) {
        this.name = name;
        this.foundation = foundation;
    }

    public void addChampionship(@Valid @NotNull Championship championship) {
        if(this.championships == null) {
            this.championships = new HashSet<>();
        }
        this.championships.add(championship);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
