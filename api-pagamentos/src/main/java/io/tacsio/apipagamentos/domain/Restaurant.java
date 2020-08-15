package io.tacsio.apipagamentos.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Restaurant extends PaymentClient {

    @NotBlank
    private String name;

    @Deprecated
    protected Restaurant() {
    }

    public Restaurant(@NotBlank String name, List<PaymentMethod> acceptedMethods) {
        super(acceptedMethods);

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
