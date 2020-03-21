package br.com.deveficiente.bolaoapi.services.user.api.model;

import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
public class UserResponse {
    @Getter
    private final Long id;
    @Getter
    private final String login;
    @Getter
    private final LocalDateTime timestamp;

    public UserResponse(@Valid @NotNull User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.timestamp = user.getTimestamp();
    }
}
