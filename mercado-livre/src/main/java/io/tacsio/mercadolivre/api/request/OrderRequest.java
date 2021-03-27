package io.tacsio.mercadolivre.api.request;

import io.tacsio.mercadolivre.data.ProductRepository;
import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.model.order.Order;
import io.tacsio.mercadolivre.service.payment.Gateway;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

public class OrderRequest {
    @Size(min = 1)
    private List<@Valid ItemRequest> cart;
    private Gateway gateway;

    public Order toModel(User user, ProductRepository productRepository) {
        var items = cart.stream()
                .map(it -> it.toModel(productRepository))
                .collect(Collectors.toList());

        return new Order(user, items, gateway);
    }


    public List<ItemRequest> getCart() {
        return cart;
    }

    public void setCart(List<ItemRequest> cart) {
        this.cart = cart;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }


}
