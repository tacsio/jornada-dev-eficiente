package io.tacsio.mercadolivre.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private Integer score;

    @NotBlank
    private String title;

    @Lob
    @Length(max = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Review() {
    }

    public Review(Integer score, String title, String description, Product product, User user) {
        this.score = score;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    public Integer getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }
}
