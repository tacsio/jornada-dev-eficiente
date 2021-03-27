package io.tacsio.mercadolivre.api.request;

import io.tacsio.mercadolivre.data.ProductRepository;
import io.tacsio.mercadolivre.model.Product;
import io.tacsio.mercadolivre.model.order.Item;
import io.tacsio.mercadolivre.validation.Exists;

import javax.validation.constraints.Positive;

public class ItemRequest {
    @Exists(entityClass = Product.class)
    private Long productId;
    @Positive
    private Integer quantity;

    public Item toModel(ProductRepository productRepository) {
        var product = productRepository.getOne(productId);
        return new Item(product, quantity);
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
