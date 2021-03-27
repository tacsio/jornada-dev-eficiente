package io.tacsio.mercadolivre.model;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Set<Image> images;

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

        this.images = new HashSet<>();
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public boolean available(@Positive Integer quantity) {
        return this.piecesAvailable >= quantity;
    }

    public void removeFromInventory(Integer quantity) {
        Assert.isTrue(available(quantity),
                "Unable to remove from inventory, quantity unavailable.");

        this.piecesAvailable = piecesAvailable - quantity;
    }
}
