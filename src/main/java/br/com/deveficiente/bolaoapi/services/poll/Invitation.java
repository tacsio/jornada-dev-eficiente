package br.com.deveficiente.bolaoapi.services.poll;

import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@ToString(exclude = "poll")
@Entity
public class Invitation {

    @Getter
    @NotBlank
    private final String key = UUID.randomUUID().toString();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Email
    private String email;
    @NotNull
    private LocalDateTime expiration = LocalDateTime.now().plusHours(24);
    @Getter
    @ManyToOne
    private Poll poll;

    private LocalDateTime closeDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INVITED;

    protected Invitation() {
    }

    protected Invitation(String email, Poll poll) {
        this.email = email;
        this.poll = poll;
    }

    public String getAcceptLink(String host) {
        String link = new StringBuilder()
                .append(host)
                .append("/invitations")
                .append("/accept")
                .append("?key=")
                .append(this.getKey())
                .toString();

        return link;
    }

    public String getDenyLink(String host) {
        String link = new StringBuilder()
                .append(host)
                .append("/invitations")
                .append("/deny")
                .append("?key=")
                .append(this.getKey())
                .toString();

        return link;
    }

    public void accept(User invitedUser) {
        Assert.isNull(closeDate, "this invitation has already used.");
        Assert.isTrue(invitedUser.getLogin().equals(email), "This user not belongs to its invitation.");
        this.getPoll().addParticipant(invitedUser);
        this.closeDate = LocalDateTime.now();
        this.status = Status.ACCEPTED;
    }

    public void decline() {
        Assert.isNull(closeDate, "this invitation has already used.");
        this.closeDate = LocalDateTime.now();
        this.status = Status.DECLINED;
    }

    enum Status {
        INVITED, ACCEPTED, DECLINED
    }

}
