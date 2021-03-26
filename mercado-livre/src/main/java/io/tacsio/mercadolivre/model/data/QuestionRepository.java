package io.tacsio.mercadolivre.model.data;

import io.tacsio.mercadolivre.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByProduct_Id(Long productId);
}
