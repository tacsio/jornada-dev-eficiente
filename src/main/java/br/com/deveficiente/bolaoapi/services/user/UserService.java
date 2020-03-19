package br.com.deveficiente.bolaoapi.services.user;

import br.com.deveficiente.bolaoapi.services.user.core.User;

import javax.validation.Valid;

public interface UserService {

    User create(@Valid User user);
}
