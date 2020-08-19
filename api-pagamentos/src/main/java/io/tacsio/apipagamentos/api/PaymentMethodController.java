package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.dto.form.AvailablePaymentForm;
import io.tacsio.apipagamentos.api.dto.representer.AvailablePaymentMethod;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.service.fraud.FraudAnalyzer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    @PersistenceContext
    private EntityManager entityManager;

    private FraudAnalyzer fraudAnalyzer;

    public PaymentMethodController(FraudAnalyzer fraudAnalyzer) {
        this.fraudAnalyzer = fraudAnalyzer;
    }

    @GetMapping
    public ResponseEntity availableMethods(@Valid @RequestBody AvailablePaymentForm form) {
        Restaurant restaurant = entityManager.find(Restaurant.class, form.restaurantId());
        User user = entityManager.find(User.class, form.userId());

        Set<AvailablePaymentMethod> availableMethods = user.availablePaymentMethods(restaurant, fraudAnalyzer)
                .map(AvailablePaymentMethod::new)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(availableMethods);
    }
}
