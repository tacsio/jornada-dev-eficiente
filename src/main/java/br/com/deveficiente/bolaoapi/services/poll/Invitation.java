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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Email
    private String email;

    @NotNull
    private LocalDateTime expiration;

    @Getter
    @NotBlank
    private final String key = UUID.randomUUID().toString();

    @Getter
    @ManyToOne
    private Poll poll;

    protected Invitation() {
    }

    public Invitation(String email, Poll poll) {
        this.email = email;
        this.poll = poll;
        this.expiration = poll.getCreatedAt().plusHours(24);
    }
}
