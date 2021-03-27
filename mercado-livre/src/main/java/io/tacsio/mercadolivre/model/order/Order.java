package io.tacsio.mercadolivre.model.order;

import io.tacsio.mercadolivre.model.User;
import io.tacsio.mercadolivre.service.payment.Gateway;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<Item> items;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @PositiveOrZero
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private Gateway selectedGateway;

    private String transactionId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    public Order() {
    }

    public Order(User user, List<Item> items, Gateway selectedGateway) {
        this.items = items;
        this.user = user;
        this.selectedGateway = selectedGateway;
        this.createdAt = LocalDateTime.now();
        this.status = Status.CREATED;
        this.transactionId = UUID.randomUUID().toString();

        this.total = items.stream()
                .map(Item::getPrice)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

        items.stream()
                .forEach(it -> it.setOrder(this));

    }

    public List<Item> getItems() {
        return items;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Gateway getSelectedGateway() {
        return selectedGateway;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Status getStatus() {
        return status;
    }
}
