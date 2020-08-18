package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.dto.AvailablePaymentForm;
import io.tacsio.apipagamentos.api.dto.AvailablePaymentMethod;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
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

    @GetMapping("/available")
    public ResponseEntity availableMethods(@Valid @RequestBody AvailablePaymentForm form) {
        Restaurant restaurant = entityManager.find(Restaurant.class, form.restaurantId());
        User user = entityManager.find(User.class, form.userId());

        Set<AvailablePaymentMethod> availableMethods = user.availablePaymentMethods(restaurant)
                .map(AvailablePaymentMethod::new)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(availableMethods);
    }
}
