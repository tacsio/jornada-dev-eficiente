package io.tacsio.mercadolivre.model.data;

import io.tacsio.mercadolivre.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
