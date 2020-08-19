package io.tacsio.apipagamentos.service.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order", url = "${service.orders.get}")
public interface OrderService {

    @GetMapping("/orders/{id}")
    OrderResponse getOrder(@PathVariable("id") Long id);
}

