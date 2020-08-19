package io.tacsio.apipagamentos.service;

import io.tacsio.apipagamentos.domain.PaymentClient;
import io.tacsio.apipagamentos.domain.PaymentMethod;
import io.tacsio.apipagamentos.domain.PaymentType;
import io.tacsio.apipagamentos.domain.Restaurant;

import java.util.Set;

/**
 * Debtor restaurants aren't allowed to pay providers with checks.
 */
public class RestaurantDebtor implements FraudChecker {

    private Set<String> debtors = Set.of("Sr. dos Calotes");

    @Override
    public <Payer extends PaymentClient> boolean accept(Payer payer, PaymentMethod paymentMethod) {
        if (payer instanceof Restaurant restaurant) {
            return !PaymentType.CHECK.equals(paymentMethod.getType()) || !debtors.contains(restaurant.getName());
        }
        return true;
    }
}
