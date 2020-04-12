package br.com.deveficiente.bolaoapi.services.poll;

import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "poll_id"}, name = "uk_poll_participant"))
public class Participant {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @OneToOne
    private User account;

    @Getter
    @ManyToOne
    private Poll poll;

    @Getter
    @OneToMany(orphanRemoval = true, mappedBy = "participant")
    @Cascade(value = {MERGE, PERSIST, REFRESH})
    private Set<Shot> shots = new HashSet<>();

    protected Participant() {
    }

    public Participant(@Valid User account, Poll poll) {
        this.account = account;
        this.poll = poll;
    }

    public boolean hasDoubledShotInRound(Integer round) {
        return this.getShots().stream()
                .filter(shot -> Objects.equals(shot.getMatch().getRound(), round))
                .anyMatch(Shot::getDoubled);
    }

    public Integer getRoundScore(Integer round) {
        return this.getShots().stream()
                .filter(shot -> Objects.equals(shot.getMatch().getRound(), round))
                .map(Shot::processShotScore)
                .reduce(0, Integer::sum);
    }

    public String getLogin() {
        return this.getAccount().getLogin();
    }
}
