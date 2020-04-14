package br.com.deveficiente.bolaoapi.services.poll;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundResultRepository extends JpaRepository<RoundResult, Long> {

    void deleteRoundResultsByRound(Integer round);
}
