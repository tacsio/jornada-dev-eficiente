package io.tacsio.apipagamentos.api.dto.representer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tacsio.apipagamentos.domain.Transaction;

import java.time.LocalDateTime;

public class PaymentTransaction {
    private final String id;
    private final String status;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private final LocalDateTime createdAt;

    public PaymentTransaction(Transaction transaction) {
        this.id = transaction.getId().toString();
        this.status = transaction.getStatus().name();
        this.createdAt = transaction.getCreatedAt();
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
