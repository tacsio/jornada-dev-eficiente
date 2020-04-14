package br.com.deveficiente.bolaoapi.services.championship.api;

import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.championship.MatchResult;
import br.com.deveficiente.bolaoapi.services.championship.api.model.match.CreateMatchResultRequest;
import br.com.deveficiente.bolaoapi.services.championship.api.model.match.MatchResultResponse;
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

    private final EntityManager entityManager;

    //TODO: remove when add spring security features
    private final UserRepository userRepository;

    public MatchController(EntityManager entityManager, UserRepository userRepository) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/{id}/result")
    @Transactional
    public ResponseEntity defineMatchResult(@Exists(entityClass = Match.class)
                                            @Unique(entityClass = MatchResult.class, entityField = "match")
                                            @PathVariable Long id,
                                            @Valid @RequestBody CreateMatchResultRequest request) {

        Match match = entityManager.find(Match.class, id);
        MatchResult matchResult = request.toMatchResult(match);

        entityManager.persist(matchResult);

        return ResponseEntity.ok(new MatchResultResponse(matchResult));
    }
}
