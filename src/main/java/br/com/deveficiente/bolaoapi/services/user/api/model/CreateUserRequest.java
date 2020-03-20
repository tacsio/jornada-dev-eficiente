package br.com.deveficiente.bolaoapi.services.user.api.model;

import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {

    @NotBlank(message = "login should not be null")
    @Email(message = "wrong format login format")
    private String login;

    @NotBlank(message = "password should not be null")
    @Length(message = "password should have at least 6 characters", min = 6)
    private String password;

    public User user() {
        return new User(this.login, this.password);
    }
}
