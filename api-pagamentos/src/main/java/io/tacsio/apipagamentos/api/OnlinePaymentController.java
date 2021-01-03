package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.form.OnlinePaymentForm;
import io.tacsio.apipagamentos.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RestController
@RequestMapping("/online")
public class OnlinePaymentController {

    private final EntityManager entityManager;
    private final OrderService orderService;

    public OnlinePaymentController(EntityManager entityManager, OrderService orderService) {
        this.entityManager = entityManager;
        this.orderService = orderService;
    }

    @PostMapping("/process")
    public ResponseEntity process(@RequestBody @Valid OnlinePaymentForm form) {



        return ResponseEntity.ok().build();
    }
}
