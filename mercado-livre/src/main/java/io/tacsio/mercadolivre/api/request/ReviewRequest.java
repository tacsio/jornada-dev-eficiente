package io.tacsio.mercadolivre.api.request;

import io.tacsio.mercadolivre.model.Product;
import io.tacsio.mercadolivre.model.Review;
import io.tacsio.mercadolivre.model.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ReviewRequest {

    @Min(1)
    @Max(5)
    private Integer score;

    @NotBlank
    private String title;

    @Lob
    @Length(max = 500)
    private String description;

    public Review toModel(User user, Product product) {
        return new Review(score, title, description, product, user);
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
