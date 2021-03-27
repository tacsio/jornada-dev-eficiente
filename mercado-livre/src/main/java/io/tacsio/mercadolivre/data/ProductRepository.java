package io.tacsio.mercadolivre.data;

import io.tacsio.mercadolivre.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
