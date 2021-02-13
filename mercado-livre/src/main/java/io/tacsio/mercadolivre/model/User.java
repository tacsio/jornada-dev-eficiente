package io.tacsio.mercadolivre.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Column(unique = true)
    private String login;

    @NotBlank
    @Length(min = 6)
    private String password;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
