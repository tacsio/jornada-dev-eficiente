package io.tacsio.mercadolivre.api.representer;

import io.tacsio.mercadolivre.model.Feature;
import io.tacsio.mercadolivre.model.Image;
import io.tacsio.mercadolivre.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepresenter {
    public final Long id;
    public final String name;
    public final BigDecimal value;
    public final Integer piecesAvailable;
    public final List<FeatureRepresenter> features;
    public final String description;
    public final ThinCategoryRepresenter category;
    public final LocalDateTime createdAt;
    public final UserRepresenter owner;
    public final List<String> images;

    public ProductRepresenter(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.value = product.getValue();
        this.piecesAvailable = product.getPiecesAvailable();
        this.description = product.getDescription();
        this.category = new ThinCategoryRepresenter(product.getCategory());
        this.createdAt = product.getCreatedAt();
        this.owner = new UserRepresenter(product.getOwner());

        this.features = product.getFeatures()
                .stream()
                .map(FeatureRepresenter::new)
                .collect(Collectors.toList());

        this.images = product.getImages().stream()
                .map(Image::getUri)
                .collect(Collectors.toList());
    }
}

class FeatureRepresenter {
    public final String name;
    public final String value;

    public FeatureRepresenter(Feature feature) {
        this.name = feature.getName();
        this.value = feature.getValue();
    }
}
