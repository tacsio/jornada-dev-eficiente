package io.tacsio.mercadolivre.api.representer;

import io.tacsio.mercadolivre.model.Question;

import java.time.LocalDateTime;

public class QuestionRepresenter {
    public final String title;
    public final String body;
    public final String user;
    public final String product;
    public final Long productId;
    public final LocalDateTime createdAt;

    public QuestionRepresenter(Question question) {
        this.title = question.getTitle();
        this.body = question.getBody();
        this.user = question.getUser().getLogin();
        this.product = question.getProduct().getName();
        this.productId = question.getProduct().getId();
        this.createdAt = question.getCreatedAt();
    }
}
