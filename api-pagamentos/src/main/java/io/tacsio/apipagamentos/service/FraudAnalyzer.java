package io.tacsio.apipagamentos.service;

import io.tacsio.apipagamentos.domain.PaymentClient;
import io.tacsio.apipagamentos.domain.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class FraudAnalyzer {

    private Set<FraudChecker> pipeline = new HashSet<>();

    public FraudAnalyzer(Set<FraudChecker> pipeline) {
        this.pipeline = pipeline;
    }

    public <Payer extends PaymentClient> boolean verify(Payer payer, PaymentMethod paymentMethod) {
        return pipeline.stream()
                .map(checker -> checker.accept(payer, paymentMethod))
                .filter(result -> !result)
                .findFirst()
                .isEmpty();
    }
}
