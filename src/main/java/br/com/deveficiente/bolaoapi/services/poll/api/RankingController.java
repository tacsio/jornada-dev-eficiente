package br.com.deveficiente.bolaoapi.services.poll.api;

import br.com.deveficiente.bolaoapi.services.poll.Poll;
import br.com.deveficiente.bolaoapi.services.poll.RankingResult;
import br.com.deveficiente.bolaoapi.services.poll.RoundResultRepository;
import br.com.deveficiente.bolaoapi.services.poll.api.model.ranking.RankingResponse;
import br.com.deveficiente.bolaoapi.shared.validator.Exists;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Validated
@RestController
public class RankingController {

    private final RoundResultRepository roundResultRepository;

    public RankingController(RoundResultRepository roundResultRepository) {
        this.roundResultRepository = roundResultRepository;
    }

    @GetMapping("/ranking")
    public ResponseEntity getPollRanking(@RequestParam(name = "pollId") @Exists(entityClass = Poll.class) Long pollId) {
        List<RankingResult> ranking = roundResultRepository.getPollRanking(pollId);

        List<RankingResponse> response = ranking.stream()
                .map(RankingResponse::new)
                .collect(toList());

        return ResponseEntity.ok(response);
    }
}
