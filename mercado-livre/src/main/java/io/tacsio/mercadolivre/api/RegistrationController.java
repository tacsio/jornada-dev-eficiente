package io.tacsio.mercadolivre.api;

import io.tacsio.mercadolivre.api.representer.UserRepresenter;
import io.tacsio.mercadolivre.api.request.NewUserRequest;
import io.tacsio.mercadolivre.model.data.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public RegistrationController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public ResponseEntity<?> signIn(@Valid @RequestBody NewUserRequest request) {

        var newUser = request.toUser(passwordEncoder);
        userRepository.save(newUser);

        return ResponseEntity.ok(new UserRepresenter(newUser));
    }

}