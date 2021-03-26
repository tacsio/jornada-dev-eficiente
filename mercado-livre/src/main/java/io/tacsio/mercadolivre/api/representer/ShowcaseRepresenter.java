package io.tacsio.mercadolivre.api.representer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tacsio.mercadolivre.model.Product;
import io.tacsio.mercadolivre.model.Question;
import io.tacsio.mercadolivre.model.Review;

import java.util.List;
import java.util.stream.Collectors;

public class ShowcaseRepresenter {
    public final ProductRepresenter product;
    @JsonIgnoreProperties({"productId", "product"})
    public final List<ReviewRepresenter> reviews;
    public final Double reviewsScoreMean;
    @JsonIgnoreProperties({"productId", "product"})
    public final List<QuestionRepresenter> questions;

    public ShowcaseRepresenter(Product product, List<Review> reviews, List<Question> questions) {
        this.product = new ProductRepresenter(product);
        this.reviews = reviews.stream()
                .map(ReviewRepresenter::new)
                .collect(Collectors.toList());

        this.questions = questions.stream()
                .map(QuestionRepresenter::new)
                .collect(Collectors.toList());

        this.reviewsScoreMean = reviews.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);
    }
}
