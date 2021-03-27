package io.tacsio.mercadolivre.model.order;

import io.tacsio.mercadolivre.model.Product;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @PositiveOrZero
    private BigDecimal price;

    @Positive
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Item() {
    }

    public Item(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = product.getValue();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
