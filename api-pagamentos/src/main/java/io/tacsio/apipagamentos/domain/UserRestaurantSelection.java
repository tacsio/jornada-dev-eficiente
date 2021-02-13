package io.tacsio.apipagamentos.domain;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"USER_ID", "RESTAURANT_ID"})
)
public class UserRestaurantSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(columnDefinition = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(columnDefinition = "RESTAURANT_ID")
    private Restaurant restaurant;

    @PositiveOrZero
    private int selectionCount;

    @Deprecated
    protected UserRestaurantSelection() {
    }

    public UserRestaurantSelection(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public int getSelectionCount() {
        return selectionCount;
    }

    public void select() {
        this.selectionCount++;
    }
}
