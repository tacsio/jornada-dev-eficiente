package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.form.OnlinePaymentForm;
import io.tacsio.apipagamentos.api.representer.PaymentTransaction;
import io.tacsio.apipagamentos.domain.data.TransactionRepository;
import io.tacsio.apipagamentos.service.gateway.CardInfo;
import io.tacsio.apipagamentos.service.gateway.GatewayService;
import io.tacsio.apipagamentos.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RestController
@RequestMapping("/online")
public class OnlinePaymentController {

    private final EntityManager entityManager;
    private final OrderService orderService;
    private final GatewayService gatewayService;
    private final TransactionRepository transactionRepository;


    public OnlinePaymentController(EntityManager entityManager, OrderService orderService, GatewayService gatewayService, TransactionRepository transactionRepository) {
        this.entityManager = entityManager;
        this.orderService = orderService;
        this.gatewayService = gatewayService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/process")
    public ResponseEntity process(@RequestBody @Valid OnlinePaymentForm form) {
        var transaction = form.getTransaction(entityManager, orderService);

        var cardInfo = new CardInfo(
            form.cardNumber(),
            form.securityCode(),
            transaction.getPaymentMethod().getBrand().get());

        var gatewayResponse = gatewayService.processPayment(cardInfo, transaction.getValue());

        transaction.setGatewayTransactionId(gatewayResponse.id);
        transaction.setOnlineStatus(gatewayResponse.success);
        transactionRepository.save(transaction);

        if (!gatewayResponse.success) {
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED);
        }

        return ResponseEntity.ok(new PaymentTransaction(transaction));
    }
}
