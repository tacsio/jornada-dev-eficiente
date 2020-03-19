package br.com.deveficiente.bolaoapi.services.user.api.model;

import br.com.deveficiente.bolaoapi.services.user.core.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CreateRequest {

    @NotBlank(message = "login should not be null")
    @Email(message = "wrong format login format", regexp = ".+@.+\\..+")
    private String login;

    @NotBlank(message = "timestamp should not be null")
    @Length(message = "password should have at least 6 characters", min = 6)
    private String password;

    private LocalDateTime timestamp = LocalDateTime.now();

    public User user() {
        return new User(this.login, this.password, this.timestamp);
    }
}
