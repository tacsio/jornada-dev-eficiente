package io.tacsio.mercadolivre.service.order;

import io.tacsio.mercadolivre.data.OrderRepository;
import io.tacsio.mercadolivre.data.ProductRepository;
import io.tacsio.mercadolivre.model.order.Item;
import io.tacsio.mercadolivre.model.order.Order;
import io.tacsio.mercadolivre.service.payment.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.paymentService = paymentService;
    }

    @Transactional
    public ProcessResult processOrder(Order order) throws ProductUnavailableException {
        //update inventory
        updateInventory(order.getItems());
        //store order
        var storedOrder = orderRepository.save(order);
        //process payment
        var gatewayURI = paymentService.processOrder(order);

        return new ProcessResult(storedOrder, gatewayURI);
    }

    @Transactional
    void updateInventory(List<Item> items) throws ProductUnavailableException {
        var unavailable = items.stream()
                .filter(item -> !item.getProduct().available(item.getQuantity()))
                .findAny();

        if (unavailable.isPresent()) {
            throw new ProductUnavailableException(unavailable.get().getQuantity() + " "
                    + unavailable.get().getProduct().getName() + " is unavailable.");
        }

        var products = items.stream()
                .map(item -> {
                    item.getProduct().removeFromInventory(item.getQuantity());
                    return item.getProduct();
                }).collect(Collectors.toList());

        productRepository.saveAll(products);
    }
}
