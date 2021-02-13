package io.tacsio.apipagamentos.api;

import io.tacsio.apipagamentos.api.form.AvailablePaymentForm;
import io.tacsio.apipagamentos.api.representer.AvailablePaymentMethod;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import io.tacsio.apipagamentos.domain.UserRestaurantSelection;
import io.tacsio.apipagamentos.domain.data.UserRestaurantSelectionRepository;
import io.tacsio.apipagamentos.service.fraud.FraudAnalyzer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private final EntityManager em;
    private final FraudAnalyzer fraudAnalyzer;
    private final UserRestaurantSelectionRepository restaurantSelectionRepository;


    @Value("${user-restaurant.favorite.count}")
    private int favoriteCount;
    @Value("${user-restaurant.favorite.expire-days}")
    private int expiresDays;

    public PaymentMethodController(EntityManager em,
                                   FraudAnalyzer fraudAnalyzer,
                                   UserRestaurantSelectionRepository restaurantSelectionRepository) {
        this.em = em;
        this.fraudAnalyzer = fraudAnalyzer;
        this.restaurantSelectionRepository = restaurantSelectionRepository;
    }

    @GetMapping
    public ResponseEntity availableMethods(@Valid @RequestBody AvailablePaymentForm form) {
        var restaurant = em.find(Restaurant.class, form.restaurantId());
        var user = em.find(User.class, form.userId());

        var availableMethods = user.availablePaymentMethods(restaurant, fraudAnalyzer)
            .map(AvailablePaymentMethod::new)
            .collect(Collectors.toSet());

        var selection = registerSelection(user, restaurant);

        return ResponseEntity.ok()
            .headers(favoriteHeader(selection))
            .body(availableMethods);
    }

    @Transactional
    UserRestaurantSelection registerSelection(User user, Restaurant restaurant) {
        var selection = restaurantSelectionRepository.findByUserAndRestaurant(user, restaurant);

        if (selection == null) {
            selection = new UserRestaurantSelection(user, restaurant);
        }
        selection.select();

        return restaurantSelectionRepository.save(selection);
    }

    private HttpHeaders favoriteHeader(UserRestaurantSelection userRestaurant) {
        if (userRestaurant.getSelectionCount() < favoriteCount) return null;

        String expiresHeader = LocalDateTime.now()
            .plusDays(expiresDays)
            .format(DateTimeFormatter.ISO_DATE_TIME);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.EXPIRES, expiresHeader);

        return httpHeaders;
    }
}
