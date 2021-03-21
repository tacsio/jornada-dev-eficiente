package io.tacsio.mercadolivre.model;

import javax.persistence.*;

@Entity
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String value;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    public Feature() {
    }

    public Feature(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
