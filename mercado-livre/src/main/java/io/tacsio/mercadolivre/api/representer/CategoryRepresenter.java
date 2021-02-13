package io.tacsio.mercadolivre.api.representer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.tacsio.mercadolivre.model.Category;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class CategoryRepresenter {
    public final Long id;
    public final String name;

    public ThinCategoryRepresenter parent;

    public final List<ThinCategoryRepresenter> children;

    public CategoryRepresenter(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        if (category.getParent() != null) this.parent = new ThinCategoryRepresenter(category.getParent());
        this.children = category.getChildren().stream()
                .map(ThinCategoryRepresenter::new)
                .collect(Collectors.toList());

    }
}
