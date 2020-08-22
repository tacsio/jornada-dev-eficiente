package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.dto.form.OfflinePaymentForm;
import io.tacsio.apipagamentos.service.order.OrderResponse;
import io.tacsio.apipagamentos.validator.util.ValidationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/offline")
public class OfflinePaymentController {

    private ValidationContext context;

    public OfflinePaymentController(ValidationContext context) {
        this.context = context;
    }

    @PostMapping("/process")
    public ResponseEntity offlinePayment(@RequestBody @Valid OfflinePaymentForm form) {

        OrderResponse order = context.get(OrderResponse.class, form.orderId().toString());

        //TODO: create transaction
        System.out.println(order);
        System.out.println(form);
        return ResponseEntity.accepted().build();
    }

}
