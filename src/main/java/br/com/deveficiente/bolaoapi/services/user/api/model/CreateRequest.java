package br.com.deveficiente.bolaoapi.services.user.api.model;

import br.com.deveficiente.bolaoapi.services.user.core.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRequest {

    @NotNull(message = "timestamp should not be null")
    private Date timestamp;

    @NotNull(message = "login should not be null")
    @Email(message = "wrong format login format", regexp = ".+@.+\\..+")
    private String login;

    @NotNull(message = "timestamp should not be null")
    @Length(message = "password should have at least 6 characters", min = 6)
    private String password;

    public User user() {

        User user = User.builder()
                .login(this.login)
                .timestamp(this.timestamp)
                .password(this.password)
                .build();

        return user;
    }
}
