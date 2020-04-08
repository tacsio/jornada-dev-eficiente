package br.com.deveficiente.bolaoapi.services.championship.api;

import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.championship.MatchRepository;
import br.com.deveficiente.bolaoapi.services.championship.Shot;
import br.com.deveficiente.bolaoapi.services.championship.api.model.CreateShotRequest;
import br.com.deveficiente.bolaoapi.services.championship.api.model.ShotResponse;
import br.com.deveficiente.bolaoapi.services.user.User;
import br.com.deveficiente.bolaoapi.services.user.UserRepository;
import br.com.deveficiente.bolaoapi.shared.validator.Exists;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchRepository matchRepository;

    //TODO: remove when add spring security features
    private final UserRepository userRepository;

    public MatchController(MatchRepository matchRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
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
}
