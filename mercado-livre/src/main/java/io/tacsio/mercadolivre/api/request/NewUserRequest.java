package io.tacsio.mercadolivre.api.request;

import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.validation.Unique;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NewUserRequest {
    @Email
    @Unique(entityClass = User.class, entityField = "login")
    private String login;

    @NotBlank
    @Length(min = 6)
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        var encryptedPassword = passwordEncoder.encode(password);
        var newUser = new User(login, encryptedPassword);

        return newUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
