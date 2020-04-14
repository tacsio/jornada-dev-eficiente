package br.com.deveficiente.bolaoapi.services.poll;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.*;

@ToString
@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @OneToOne
    private User owner;

    @Getter
    @NotNull
    @OneToOne
    private Championship championship;

    @Getter
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Getter
    @Size(min = 1)
    @OneToMany(mappedBy = "poll", cascade = {MERGE, REFRESH, REMOVE, PERSIST}, orphanRemoval = true)
    private final Set<Invitation> invitations = new HashSet<>();

    @Getter
    @OneToMany(cascade = {PERSIST, MERGE, REFRESH}, orphanRemoval = true, mappedBy = "poll")
    private final Set<Participant> participants = new HashSet<>();

    protected Poll() {
    }

    public Poll(User owner, Championship championship, @Size(min = 1) Set<@Email String> emails) {
        this.owner = owner;
        this.championship = championship;
        this.invitations.addAll(buildInvitations(emails));
        this.participants.add(new Participant(owner, this));
    }

    private Set<Invitation> buildInvitations(Set<String> emails) {
        Set<Invitation> invitations = emails.stream()
                .map(email -> new Invitation(email, this))
                .collect(Collectors.toSet());

        return invitations;
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }

    public List<RoundResult> processRound(@Positive Integer round) {
        List<RoundResult> results = participants.stream()
                .map(participant -> {
                    Integer score = participant.getRoundScore(round);
                    return new RoundResult(participant, this, round, score, LocalDateTime.now());
                }).collect(Collectors.toList());

        return results;
    }
}