package io.tacsio.mercadolivre.api;

import io.tacsio.mercadolivre.api.representer.ReviewRepresenter;
import io.tacsio.mercadolivre.api.request.ReviewRequest;
import io.tacsio.mercadolivre.model.Product;
import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.data.ProductRepository;
import io.tacsio.mercadolivre.data.ReviewRepository;
import io.tacsio.mercadolivre.validation.Exists;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Validated
@RestController
public class ReviewController {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ReviewController(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/products/{id}/reviews")
    @Transactional
    public ResponseEntity create(@AuthenticationPrincipal User user,
                                 @PathVariable("id") @Exists(entityClass = Product.class) Long productId,
                                 @RequestBody @Valid ReviewRequest request) {

        var product = productRepository.getOne(productId);
        var review = request.toModel(user, product);

        reviewRepository.save(review);

        return ResponseEntity.ok(new ReviewRepresenter(review));
    }

    @GetMapping("/products/{id}/reviews")
    public ResponseEntity index(@PathVariable("id") @Exists(entityClass = Product.class) Long productId) {

        var reviews = this.reviewRepository.findAllByProduct_Id(productId);

        var response = reviews.stream()
                .map(ReviewRepresenter::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
