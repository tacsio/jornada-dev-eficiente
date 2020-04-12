package br.com.deveficiente.bolaoapi.services.championship.api;

import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.championship.MatchRepository;
import br.com.deveficiente.bolaoapi.services.championship.MatchResult;
import br.com.deveficiente.bolaoapi.services.championship.Shot;
import br.com.deveficiente.bolaoapi.services.championship.api.model.CreateMatchResultRequest;
import br.com.deveficiente.bolaoapi.services.championship.api.model.CreateShotRequest;
import br.com.deveficiente.bolaoapi.services.championship.api.model.MatchResultResponse;
import br.com.deveficiente.bolaoapi.services.championship.api.model.ShotResponse;
import br.com.deveficiente.bolaoapi.services.user.User;
import br.com.deveficiente.bolaoapi.services.user.UserRepository;
import br.com.deveficiente.bolaoapi.shared.validator.Exists;
import br.com.deveficiente.bolaoapi.shared.validator.Unique;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchRepository matchRepository;
    private final EntityManager entityManager;

    //TODO: remove when add spring security features
    private final UserRepository userRepository;

    public MatchController(MatchRepository matchRepository, EntityManager entityManager, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/{id}/shots")
    @Transactional
    public ResponseEntity createShot(@Exists(entityClass = Match.class) @PathVariable Long id, @Valid @RequestBody CreateShotRequest request) {
        //TODO: remove when add spring security features
        User loggedUser = userRepository.findById(1l).get();
        Match match = matchRepository.findById(id).get();

        Shot shot = request.toShot(loggedUser, match);
        match.addShot(shot);

        return ResponseEntity.ok(new ShotResponse(shot));
    }

    @PostMapping("/{id}/result")
    @Transactional
    public ResponseEntity defineMatchResult(@Exists(entityClass = Match.class)
                                            @Unique(entityClass = MatchResult.class, entityField = "match")
                                            @PathVariable Long id,
                                            @Valid @RequestBody CreateMatchResultRequest request) {

        Match match = matchRepository.findById(id).get();
        MatchResult matchResult = request.toMatchResult(match);

        entityManager.persist(matchResult);

        return ResponseEntity.ok(new MatchResultResponse(matchResult));
    }
}
