package br.com.deveficiente.bolaoapi.services.user.api;

import br.com.deveficiente.bolaoapi.services.user.UserService;
import br.com.deveficiente.bolaoapi.services.user.api.model.CreateRequest;
import br.com.deveficiente.bolaoapi.services.user.api.model.ErrorResponse;
import br.com.deveficiente.bolaoapi.services.user.api.model.UserResponse;
import br.com.deveficiente.bolaoapi.services.user.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> index() {
        List<UserResponse> users = this.userService.getAll().stream()
                .map(user -> UserResponse.fromUser(user))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreateRequest request) {
        try {
            UserResponse response = UserResponse.fromUser(this.userService.create(request.user()));
            return ResponseEntity.ok(response);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.badRequest().body(e.getConstraintViolations());
        } catch (Exception e) {
            ErrorResponse error = ErrorResponse.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(error);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
