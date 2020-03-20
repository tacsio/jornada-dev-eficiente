package br.com.deveficiente.bolaoapi.services.user.api;

import br.com.deveficiente.bolaoapi.services.user.UserRepository;
import br.com.deveficiente.bolaoapi.services.user.api.model.CreateUserRequest;
import br.com.deveficiente.bolaoapi.services.user.api.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreateUserRequest request) {
        UserResponse response = new UserResponse(this.userRepository.save(request.user()));
        return ResponseEntity.ok(response);
    }
}
