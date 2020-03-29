package br.com.deveficiente.bolaoapi.services.poll;

import lombok.Getter;
import lombok.ToString;

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

    protected Invitation() {
    }

    protected Invitation(String email, Poll poll) {
        this.email = email;
        this.poll = poll;
    }

    public String getInvitationLink() {
        String link = new StringBuilder()
                .append("http://localhost:8080")
                .append("/invitations?key=")
                .append(this.getKey())
                .toString();

        return link;
    }
}
