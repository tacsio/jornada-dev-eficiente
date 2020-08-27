package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.dto.form.OfflinePaymentForm;
import io.tacsio.apipagamentos.api.dto.representer.PaymentTransaction;
import io.tacsio.apipagamentos.domain.Transaction;
import io.tacsio.apipagamentos.domain.TransactionStatus;
import io.tacsio.apipagamentos.domain.data.TransactionRepository;
import io.tacsio.apipagamentos.validator.Exists;
import io.tacsio.apipagamentos.validator.util.ValidationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/offline")
public class OfflinePaymentController {

    private final ValidationContext context;
    private final TransactionRepository transactionRepository;

    public OfflinePaymentController(ValidationContext context, TransactionRepository transactionRepository) {
        this.context = context;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/process")
    @Transactional
    public ResponseEntity offlinePayment(@RequestBody @Valid OfflinePaymentForm form) {

        Transaction paymentTransaction =
                transactionRepository.find(form.orderId(), form.userId(), form.restaurantId(), form.paymentMethodId());

        if (paymentTransaction == null) {
            paymentTransaction = form.getTransaction(context);
            transactionRepository.save(paymentTransaction);
        }

        return ResponseEntity.accepted().body(new PaymentTransaction(paymentTransaction));
    }

    @PostMapping("/conclude/{transactionId}")
    @Transactional
    public ResponseEntity concludeTransaction(@Exists(entityClass = Transaction.class) @PathVariable UUID transactionId) {
        var transaction = context.get(Transaction.class, transactionId);

        if (Objects.equals(transaction.getStatus(), TransactionStatus.AWAITING_PAYMENT_CONFIRMATION)) {
            transaction.conclude();
            transactionRepository.saveAndFlush(transaction);
        }

        return ResponseEntity.ok(new PaymentTransaction(transaction));
    }

}
