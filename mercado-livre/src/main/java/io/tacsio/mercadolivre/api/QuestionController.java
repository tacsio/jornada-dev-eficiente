package io.tacsio.mercadolivre.api;

import io.tacsio.mercadolivre.api.representer.QuestionRepresenter;
import io.tacsio.mercadolivre.api.request.QuestionRequest;
import io.tacsio.mercadolivre.model.Product;
import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.data.ProductRepository;
import io.tacsio.mercadolivre.data.QuestionRepository;
import io.tacsio.mercadolivre.service.mail.MailService;
import io.tacsio.mercadolivre.validation.Exists;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products/{id}/questions")
public class QuestionController {

    private final ProductRepository productRepository;
    private final QuestionRepository questionRepository;
    private final MailService mailService;

    public QuestionController(ProductRepository productRepository, QuestionRepository questionRepository, MailService mailService) {
        this.productRepository = productRepository;
        this.questionRepository = questionRepository;
        this.mailService = mailService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@AuthenticationPrincipal User user,
                                 @PathVariable("id") @Exists(entityClass = Product.class) Long productId,
                                 @Valid @RequestBody QuestionRequest request) {

        var product = productRepository.getOne(productId);
        var question = request.toModel(user, product);

        questionRepository.save(question);
        //send notification
        CompletableFuture.runAsync(() -> mailService.sendMail(question));

        return ResponseEntity.ok(new QuestionRepresenter(question));
    }

    @GetMapping
    public ResponseEntity index(@PathVariable("id") @Exists(entityClass = Product.class) Long productId) {

        var reviews = this.questionRepository.findAllByProduct_Id(productId);

        var response = reviews.stream()
                .map(QuestionRepresenter::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
