package io.tacsio.apipagamentos.service;

import io.tacsio.apipagamentos.domain.PaymentClient;
import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * For denied users, only offline payments are allowed
 */
@Service
public class UserFraudster implements FraudChecker {

    private Set<String> deniedUsers = new HashSet<>();

    public UserFraudster() {
        this.deniedUsers.add("fraudster@frauders.com");
    }


    @Override
    public <Payer extends PaymentClient> boolean accept(Payer payer, PaymentMethod paymentMethod) {
        if (payer instanceof User user) {
            return !paymentMethod.isOnline() || !deniedUsers.contains(user.getEmail());
        }
        return true;
    }
}
