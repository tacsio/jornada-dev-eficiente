package io.tacsio.apipagamentos.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import java.util.List;

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

    public String getEmail() {
        return email;
    }
}
