package io.tacsio.mercadolivre.data;

import io.tacsio.mercadolivre.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByProduct_Id(Long productId);
}
