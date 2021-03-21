package io.tacsio.mercadolivre.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal value;
    private Integer piecesAvailable;
    @OneToMany(orphanRemoval = true, mappedBy = "product", cascade = CascadeType.ALL)
    private List<Feature> features;

    @Lob
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User owner;

    public Product() {
    }

    public Product(String name,
                   BigDecimal value,
                   Integer piecesAvailable,
                   List<Feature> features,
                   String description,
                   Category category,
                   User owner) {

        this.name = name;
        this.value = value;
        this.piecesAvailable = piecesAvailable;
        this.features = features;
        this.description = description;
        this.category = category;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();

        this.features.stream()
                .forEach(f -> f.setProduct(this));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Integer getPiecesAvailable() {
        return piecesAvailable;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getOwner() {
        return owner;
    }
}
