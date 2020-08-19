package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.dto.form.OfflinePaymentForm;
import io.tacsio.apipagamentos.service.order.OrderResponse;
import io.tacsio.apipagamentos.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/offline")
public class OfflinePaymentController {

    private OrderService orderService;

    public OfflinePaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/process")
    public ResponseEntity offlinePayment(@RequestBody @Valid OfflinePaymentForm form) {

        OrderResponse order = orderService.getOrder(form.orderId());

        //TODO: create transaction
        System.out.println(order);
        System.out.println(form);
        return ResponseEntity.accepted().build();
    }

}
