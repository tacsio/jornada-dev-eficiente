package br.com.deveficiente.bolaoapi.services.poll;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoundResultRepository extends JpaRepository<RoundResult, Long> {

    void deleteRoundResultsByRound(Integer round);

    String POLL_RANKING_QUERY = """
            select new br.com.deveficiente.bolaoapi.services.poll.RankingResult(p, sum(r.score))
            from RoundResult r, Participant p 
            where r.poll.id=?1 and p = r.participant
            group by p
            order by sum(r.score) desc
            """;
    @Query(POLL_RANKING_QUERY)
    List<RankingResult> getPollRanking(Long pollId);
}
