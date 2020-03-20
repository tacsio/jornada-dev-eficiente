package br.com.deveficiente.bolaoapi.services.user;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Entity(name = "poll_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotBlank
    @Email(regexp = ".+@.+\\..+")
    @Getter
    private String login;

    @NotBlank
    @Getter
    private String password;

    @NotNull
    @Getter
    private LocalDateTime timestamp;

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {
    }

    public User(@NotBlank String login, @NotBlank String rawPassword) {
        this.login = login;
        this.password = encoder.encode(rawPassword);
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
