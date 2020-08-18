package io.tacsio.apipagamentos.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.stream.Stream;

@Entity
public class User extends PaymentClient {

    @Email
    private String email;

    @Deprecated
    protected User() {
    }

    public User(@Email String email, List<PaymentMethod> paymentMethods) {
        super(paymentMethods);
        this.email = email;
    }

    public <T extends PaymentClient> Stream<PaymentMethod> availablePaymentMethods(T receiver) {
        return this.getPaymentMethods().stream()
                .distinct()
                .filter(receiver.getPaymentMethods()::contains);
    }

    public String getEmail() {
        return email;
    }
}
