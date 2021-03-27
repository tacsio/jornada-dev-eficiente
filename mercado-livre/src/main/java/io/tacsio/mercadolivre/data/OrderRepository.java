package io.tacsio.mercadolivre.data;

import io.tacsio.mercadolivre.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
