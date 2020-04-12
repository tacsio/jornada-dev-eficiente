package br.com.deveficiente.bolaoapi.services.poll.api;

import br.com.deveficiente.bolaoapi.services.poll.PollRepository;
import br.com.deveficiente.bolaoapi.services.poll.RoundResult;
import br.com.deveficiente.bolaoapi.services.poll.RoundResultRepository;
import br.com.deveficiente.bolaoapi.services.poll.api.model.poll.ProcessRoundRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/scores")
public class ScoreController {

    private PollRepository pollRepository;
    private RoundResultRepository roundResultRepository;

    public ScoreController(PollRepository pollRepository, RoundResultRepository roundResultRepository) {
        this.pollRepository = pollRepository;
        this.roundResultRepository = roundResultRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity processRoundScores(@RequestBody @Valid ProcessRoundRequest request) {
        this.deleteProcessedInRound(request.getRound());

        List<RoundResult> resultList = pollRepository.findAll().stream()
                .map(poll -> poll.processRound(request.getRound()))
                .collect(flatMapping(Collection::stream, toList()));

        roundResultRepository.saveAll(resultList);
        return ResponseEntity.noContent().build();
    }

    private void deleteProcessedInRound(Integer round) {
        roundResultRepository.deleteRoundResultsByRound(round);
        roundResultRepository.flush();
    }

}
