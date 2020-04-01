package br.com.deveficiente.bolaoapi.services.poll;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Getter
    @NotNull
    @OneToOne
    private Championship championship;

    @Getter
    @Size(min = 1)
    @OneToMany(mappedBy = "poll", cascade = {MERGE, REFRESH, REMOVE, PERSIST}, orphanRemoval = true)
    private final Set<Invitation> invitations = new HashSet<>();

    @Getter
    @ManyToMany
    private final Set<User> participants = new HashSet<>();

    protected Poll() {
    }

    public Poll(User owner, Championship championship, @Size(min = 1) Set<@Email String> emails) {
        this.owner = owner;
        this.championship = championship;
        this.invitations.addAll(buildInvitations(emails));
        this.participants.add(owner);
    }

    public void addParticipant(User participant) {
        this.participants.add(participant);
    }

    private Set<Invitation> buildInvitations(Set<String> emails) {
        Set<Invitation> invitations = emails.stream()
                .map(email -> new Invitation(email, this))
                .collect(Collectors.toSet());

        return invitations;
    }
}
