package io.tacsio.apipagamentos.api.representer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.tacsio.apipagamentos.domain.Transaction;

import java.time.LocalDateTime;

public class PaymentTransaction {
    public final String id;
    public final String status;
    public final String paymentMethod;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public final LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    public final LocalDateTime updatedAt;

    public PaymentTransaction(Transaction transaction) {
        this.id = transaction.getId().toString();
        this.status = transaction.getStatus().name();
        this.paymentMethod = transaction.getPaymentMethod().getDescription();
        this.createdAt = transaction.getCreatedAt();
        this.updatedAt = transaction.getUpdatedAt();
    }
}
