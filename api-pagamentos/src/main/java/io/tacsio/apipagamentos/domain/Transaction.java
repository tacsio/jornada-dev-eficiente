package io.tacsio.apipagamentos.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {
    @Id
    private UUID id;

    @PositiveOrZero
    private BigInteger value;

    @ManyToOne
    private User user;

    @ManyToOne
    private PaymentMethod paymentMethod;

    private String extra;

    @Enumerated
    private TransactionStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void generateTransactionId() {
        id = UUID.randomUUID();
    }

    @Deprecated
    protected Transaction() {
    }

    public Transaction(@PositiveOrZero BigInteger value, User user, PaymentMethod paymentMethod, String extra) {
        this.value = value;
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.extra = extra;

        if (!paymentMethod.getType().online) {
            this.status = TransactionStatus.AWAITING_PAYMENT_CONFIRMATION;
        }
    }
}
