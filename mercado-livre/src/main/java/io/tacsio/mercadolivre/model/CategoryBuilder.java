package io.tacsio.mercadolivre.model;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class CategoryBuilder {
    private final String name;
    private Category parent;
    private SortedSet<Category> children = new TreeSet<>();

    private CategoryBuilder(String name) {
        this.name = name;
    }

    public static CategoryBuilder category(String name) {
        return new CategoryBuilder(name);
    }

    public CategoryBuilder parent(Category category) {
        this.parent = category;
        return this;
    }

    public CategoryBuilder children(Collection<Category> children) {
        if (children == null) return this;
        this.children.addAll(children);
        return this;
    }

    public CategoryBuilder child(Category category) {
        this.children.add(category);
        return this;
    }

    public Category build() {
        return new Category(name, parent, children);
    }


}