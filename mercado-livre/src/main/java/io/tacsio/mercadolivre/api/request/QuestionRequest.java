package io.tacsio.mercadolivre.api.request;

import io.tacsio.mercadolivre.model.Product;
import io.tacsio.mercadolivre.model.Question;
import io.tacsio.mercadolivre.model.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class QuestionRequest {

    @NotBlank
    private String title;

    @Length(max = 1500)
    private String body;


    public Question toModel(User user, Product product) {
        return new Question(title, body, product, user);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
