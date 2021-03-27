package io.tacsio.mercadolivre;

import io.tacsio.mercadolivre.data.CategoryRepository;
import io.tacsio.mercadolivre.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @GetMapping("/payment-redirect")
    public ResponseEntity payment() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dummy-pagseguro")
    public ResponseEntity dummyPagSeguro(@RequestParam("returnId") String id, @RequestParam("redirectUrl") String url) {
        var map = new HashMap<>();
        map.put("id", id);
        map.put("url", url);

        return ResponseEntity.ok(map);
    }

    @GetMapping("/dummy-paypal/{id)")
    public ResponseEntity dummyPaypal(@PathVariable("id") String id, @RequestParam("redirectUrl") String url) {
        var map = new HashMap<>();
        map.put("id", id);
        map.put("url", url);

        return ResponseEntity.ok(map);
    }
}