package io.tacsio.apipagamentos.domain;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PaymentClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1)
    @ManyToMany
    @JoinTable(name = "accepted_payment_methods")
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @Deprecated
    protected PaymentClient() {
    }

    public PaymentClient(List<PaymentMethod> paymentMethods) {
        Assert.notEmpty(paymentMethods, "At least 1 payment method is required.");
        this.paymentMethods.addAll(paymentMethods);
    }

    public Long getId() {
        return id;
    }

    public Set<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }
}
