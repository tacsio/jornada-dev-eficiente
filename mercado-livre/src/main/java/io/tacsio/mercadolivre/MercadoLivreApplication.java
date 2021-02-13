package io.tacsio.mercadolivre;

import io.tacsio.mercadolivre.model.data.CategoryRepository;
import io.tacsio.mercadolivre.model.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/users")
    public ResponseEntity listUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/categories")
    public ResponseEntity categories() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}