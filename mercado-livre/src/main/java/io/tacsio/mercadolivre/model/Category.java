package io.tacsio.mercadolivre.model;

import com.fasterxml.jackson.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.util.comparator.Comparators;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Comparator;
import java.util.SortedSet;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category implements Comparable<Category> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(columnDefinition = "parent_id")
    @JsonBackReference
    private Category parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @OrderBy("name")
    @JsonManagedReference
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

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public SortedSet<Category> getChildren() {
        return children;
    }

    @Override
    public int compareTo(Category o) {
        return Comparator
                .comparing(Category::getName)
                .compare(this, o);
    }
}
