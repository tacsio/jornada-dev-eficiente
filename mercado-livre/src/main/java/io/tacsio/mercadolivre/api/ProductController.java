package io.tacsio.mercadolivre.api;

import io.tacsio.mercadolivre.api.representer.ProductRepresenter;
import io.tacsio.mercadolivre.api.request.NewProductRequest;
import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.model.data.CategoryRepository;
import io.tacsio.mercadolivre.model.data.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity create(@Valid @RequestBody NewProductRequest request,
                                 @AuthenticationPrincipal User owner) {

        var product = request.toModel(owner, categoryRepository);
        productRepository.save(product);

        return ResponseEntity.ok(new ProductRepresenter(product));
    }
}
