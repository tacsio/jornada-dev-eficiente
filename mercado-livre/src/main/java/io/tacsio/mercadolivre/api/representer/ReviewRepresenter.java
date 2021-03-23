package io.tacsio.mercadolivre.api.representer;

import io.tacsio.mercadolivre.model.Review;

public class ReviewRepresenter {
    public final Integer score;
    public final String title;
    public final String description;
    public final String product;
    public final Long productId;
    public final String user;

    public ReviewRepresenter(Review review) {
        this.score = review.getScore();
        this.title = review.getTitle();
        this.description = review.getDescription();
        this.product = review.getProduct().getName();
        this.productId = review.getProduct().getId();
        this.user = review.getUser().getUsername();
    }
}
