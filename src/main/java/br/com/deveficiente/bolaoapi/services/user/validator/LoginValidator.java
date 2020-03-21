package br.com.deveficiente.bolaoapi.services.user.validator;

import br.com.deveficiente.bolaoapi.services.user.UserRepository;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class LoginValidator implements ConstraintValidator<UniqueLogin, String> {

    private final UserRepository repository;

    public LoginValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !repository.findByLogin(value).isPresent();
    }
}
