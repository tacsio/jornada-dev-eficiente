package io.tacsio.mercadolivre.api.request;

import io.tacsio.mercadolivre.model.Category;
import io.tacsio.mercadolivre.model.CategoryBuilder;
import io.tacsio.mercadolivre.model.data.CategoryRepository;
import io.tacsio.mercadolivre.validation.Exists;
import io.tacsio.mercadolivre.validation.Unique;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CategoryRequest {

    @NotBlank
    @Unique(entityClass = Category.class, entityField = "name")
    private String name;
    @Exists(entityClass = Category.class, nullable = true)
    private Long parentId;
    private List<@Exists(entityClass = Category.class) Long> childrenIds;


    public Category toModel(CategoryRepository repository) {
        Category parent = null;
        List<Category> children = null;

        if (parentId != null) parent = repository.findById(parentId).get();
        if (childrenIds != null) children = repository.findAllByIdIn(childrenIds);

        return CategoryBuilder
                .category(name)
                .parent(parent)
                .children(children)
                .build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Long> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(List<Long> childrenIds) {
        this.childrenIds = childrenIds;
    }
}
