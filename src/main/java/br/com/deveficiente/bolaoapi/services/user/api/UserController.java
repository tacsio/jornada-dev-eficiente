package br.com.deveficiente.bolaoapi.services.user.api;

import br.com.deveficiente.bolaoapi.services.user.UserService;
import br.com.deveficiente.bolaoapi.services.user.api.model.CreateRequest;
import br.com.deveficiente.bolaoapi.services.user.api.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreateRequest request) {
        UserResponse response = new UserResponse(this.userService.create(request.user()));
        return ResponseEntity.ok(response);
    }
}
