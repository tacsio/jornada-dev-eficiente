package br.com.deveficiente.bolaoapi.services.poll;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByKeyAndExpirationAfter(String key, LocalDateTime now);
}
