package io.tacsio.mercadolivre;

import io.tacsio.mercadolivre.model.data.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MercadoLivreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MercadoLivreApplication.class, args);
    }
}

@RestController
@RequestMapping("test")
class TestController {
    private final UserRepository userRepository;

    TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity listUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }
}