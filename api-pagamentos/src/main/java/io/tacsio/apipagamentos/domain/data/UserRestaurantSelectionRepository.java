package io.tacsio.apipagamentos.domain.data;

import io.tacsio.apipagamentos.domain.UserRestaurantSelection;
import io.tacsio.apipagamentos.domain.Restaurant;
import io.tacsio.apipagamentos.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRestaurantSelectionRepository extends JpaRepository<UserRestaurantSelection, Long> {
    UserRestaurantSelection findByUserAndRestaurant(User user, Restaurant restaurant);
}
