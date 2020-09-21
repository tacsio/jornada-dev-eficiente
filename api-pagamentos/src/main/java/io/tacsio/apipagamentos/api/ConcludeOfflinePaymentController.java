package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.dto.representer.PaymentTransaction;
import io.tacsio.apipagamentos.domain.Transaction;
import io.tacsio.apipagamentos.domain.TransactionStatus;
import io.tacsio.apipagamentos.domain.data.TransactionRepository;
import io.tacsio.apipagamentos.validator.Exists;
import io.tacsio.apipagamentos.validator.util.ValidationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

@Validated
@RestController
public class ConcludeOfflinePaymentController {

    private final ValidationContext context;
    private final TransactionRepository transactionRepository;

    public ConcludeOfflinePaymentController(ValidationContext context, TransactionRepository transactionRepository) {
        this.context = context;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/offline/conclude/{transactionId}")
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
