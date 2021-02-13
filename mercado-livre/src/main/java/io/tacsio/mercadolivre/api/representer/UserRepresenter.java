package io.tacsio.mercadolivre.api.representer;

import io.tacsio.mercadolivre.model.User;

import java.time.LocalDateTime;

public class UserRepresenter {
    public final String login;
    public final LocalDateTime userSince;

    public UserRepresenter(User user) {
        this.login = user.getLogin();
        this.userSince = user.getCreatedAt();
    }
}
