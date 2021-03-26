package io.tacsio.mercadolivre.model;

import io.tacsio.mercadolivre.service.mail.Notifiable;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Question implements Notifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Lob
    @Length(max = 1500)
    private String body;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Question() {
    }

    public Question(String title, String body, Product product, User user) {
        this.title = title;
        this.body = body;
        this.product = product;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String subject() {
        return title;
    }

    @Override
    public String body() {
        return body;
    }

    @Override
    public String to() {
        return product.getOwner().getLogin();
    }

    @Override
    public String from() {
        return user.getLogin();
    }
}
