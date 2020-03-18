package br.com.deveficiente.bolaoapi.services.user;

import br.com.deveficiente.bolaoapi.services.user.core.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    User create(@Valid User user);

    List<User> getAll();
}
