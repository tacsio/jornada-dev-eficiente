package io.tacsio.mercadolivre.api;

import io.tacsio.mercadolivre.api.request.OrderRequest;
import io.tacsio.mercadolivre.data.ProductRepository;
import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.service.order.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    private final ProductRepository productRepository;
    private final OrderService orderService;

    public OrderController(ProductRepository productRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity create(@AuthenticationPrincipal User user,
                                 @RequestBody @Valid OrderRequest orderRequest) {

        var order = orderRequest.toModel(user, productRepository);
        var processResult = orderService.processOrder(order);

        var headers = new HttpHeaders();
        headers.setLocation(processResult.gatewayURI);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }
}
