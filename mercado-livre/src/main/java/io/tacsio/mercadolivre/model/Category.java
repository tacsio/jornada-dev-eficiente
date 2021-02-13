package io.tacsio.mercadolivre.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.SortedSet;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(columnDefinition = "parent_id")
    @JsonIgnoreProperties({"parent, children"})
    private Category parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @OrderBy("name")
    @JsonIgnoreProperties({"parent, children"})
    private SortedSet<Category> children;

    @Deprecated
    protected Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Category parent, SortedSet<Category> children) {
        this.name = name;
        this.parent = parent;
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public SortedSet<Category> getChildren() {
        return children;
    }
}
