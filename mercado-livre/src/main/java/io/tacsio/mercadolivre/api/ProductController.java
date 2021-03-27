package io.tacsio.mercadolivre.api;

import io.tacsio.mercadolivre.api.representer.ProductRepresenter;
import io.tacsio.mercadolivre.api.request.ProductRequest;
import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.data.CategoryRepository;
import io.tacsio.mercadolivre.data.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@AuthenticationPrincipal User owner,
                                 @Valid @RequestBody ProductRequest request) {

        var product = request.toModel(owner, categoryRepository);
        productRepository.save(product);

        return ResponseEntity.ok(new ProductRepresenter(product));
    }

    @GetMapping
    public ResponseEntity index() {
        var products = productRepository.findAll();

        var response = products.stream()
                .map(ProductRepresenter::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
