package io.tacsio.apipagamentos.domain.data;

import io.tacsio.apipagamentos.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("""
            select t from Transaction t 
            where t.orderId= :orderId 
            and t.user.id= :userId 
            and t.restaurant.id = :restaurantId
            and t.paymentMethod.id = :paymentMethodId
                """)
    Transaction find(
            @Param("orderId") Long orderId,
            @Param("userId") Long userId,
            @Param("restaurantId") Long restaurantId,
            @Param("paymentMethodId") Long paymentMethodId);
}
