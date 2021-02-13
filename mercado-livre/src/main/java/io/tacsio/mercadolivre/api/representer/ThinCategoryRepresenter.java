package io.tacsio.mercadolivre.api.representer;

import io.tacsio.mercadolivre.model.Category;

public class ThinCategoryRepresenter {
    public final Long id;
    public final String name;

    public ThinCategoryRepresenter(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
