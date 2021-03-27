package io.tacsio.mercadolivre.data;

import io.tacsio.mercadolivre.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByIdIn(List<Long> ids);
}
