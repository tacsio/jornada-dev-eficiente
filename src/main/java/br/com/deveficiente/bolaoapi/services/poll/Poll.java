package br.com.deveficiente.bolaoapi.services.poll;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@ToString
@Entity
@EntityListeners(PollListener.class)
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @OneToOne
    private User owner;

    @Getter
    private LocalDateTime createdAt = LocalDateTime.now();

    @Getter
    @NotNull
    @OneToOne
    private Championship championship;

    @Getter
    @Size(min = 1)
    @OneToMany(mappedBy = "poll", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Invitation> invitations = new HashSet<>();

    protected Poll() {
    }

    public Poll(User owner, Championship championship) {
        this.owner = owner;
        this.championship = championship;
    }

    public void addInvitation(Invitation invitation) {
        this.invitations.add(invitation);
    }
}
