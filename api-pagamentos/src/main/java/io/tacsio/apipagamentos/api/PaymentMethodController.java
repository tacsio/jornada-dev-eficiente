package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.form.AvailablePaymentForm;
import io.tacsio.apipagamentos.api.representer.AvailablePaymentMethod;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.service.fraud.FraudAnalyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private final EntityManager em;
    private final FraudAnalyzer fraudAnalyzer;

    public PaymentMethodController(EntityManager em, FraudAnalyzer fraudAnalyzer) {
        this.em = em;
        this.fraudAnalyzer = fraudAnalyzer;
    }

    @GetMapping
    public ResponseEntity availableMethods(@Valid @RequestBody AvailablePaymentForm form) {
        var restaurant = em.find(Restaurant.class, form.restaurantId());
        var user = em.find(User.class, form.userId());

        var availableMethods = user.availablePaymentMethods(restaurant, fraudAnalyzer)
                .map(AvailablePaymentMethod::new)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(availableMethods);
    }
}
