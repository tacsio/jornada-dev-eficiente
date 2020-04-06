package br.com.deveficiente.bolaoapi.services.championship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    String MATCH_WITH_DOUBLED_SHOT = """
            select m from Match m
            join Shot s on (s.match.id = m.id and s.doubled is true and s.participant.id = ?3)
            where m.championship.id = ?1 
            and m.round = ?2
            """;

    @Query(MATCH_WITH_DOUBLED_SHOT)
    Optional<Match> findMatchWithDoubledShot(Long championshipId, Integer round, Long participantId);
}
