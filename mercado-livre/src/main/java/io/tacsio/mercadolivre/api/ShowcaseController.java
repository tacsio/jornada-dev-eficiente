package io.tacsio.mercadolivre.api;

import io.tacsio.mercadolivre.api.representer.ShowcaseRepresenter;
import io.tacsio.mercadolivre.model.Product;
import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.data.ProductRepository;
import io.tacsio.mercadolivre.data.QuestionRepository;
import io.tacsio.mercadolivre.data.ReviewRepository;
import io.tacsio.mercadolivre.validation.Exists;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/showcase")
public class ShowcaseController {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final QuestionRepository questionRepository;

    public ShowcaseController(ProductRepository productRepository, ReviewRepository reviewRepository, QuestionRepository questionRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity product(@AuthenticationPrincipal User user,
                                  @PathVariable("id") @Exists(entityClass = Product.class) Long productId) {

        var product = productRepository.getOne(productId);
        var reviews = reviewRepository.findAllByProduct_Id(productId);
        var questions = questionRepository.findAllByProduct_Id(productId);


        return ResponseEntity.ok(new ShowcaseRepresenter(product, reviews, questions));
    }
}
