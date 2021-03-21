package io.tacsio.mercadolivre.api.request;

import io.tacsio.mercadolivre.model.Category;
import io.tacsio.mercadolivre.model.Feature;
import io.tacsio.mercadolivre.model.Product;
import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.model.data.CategoryRepository;
import io.tacsio.mercadolivre.validation.Exists;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class NewProductRequest {
    @NotBlank
    private String name;
    @Positive
    private BigDecimal value;
    @PositiveOrZero
    private Integer piecesAvailable;
    @Size(min = 3)
    private List<FeatureRequest> features;
    @Size(max = 1000)
    private String description;
    @Exists(entityClass = Category.class)
    private Long categoryId;

    public Product toModel(User owner, CategoryRepository categoryRepository) {
        var category = categoryRepository.findById(categoryId).get();
        var features = this.features.stream()
                .map(FeatureRequest::toModel)
                .collect(Collectors.toList());

        return new Product(name, value, piecesAvailable, features, description, category, owner);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getPiecesAvailable() {
        return piecesAvailable;
    }

    public void setPiecesAvailable(Integer piecesAvailable) {
        this.piecesAvailable = piecesAvailable;
    }

    public List<FeatureRequest> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureRequest> features) {
        this.features = features;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}

class FeatureRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String value;

    public Feature toModel() {
        return new Feature(name, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
