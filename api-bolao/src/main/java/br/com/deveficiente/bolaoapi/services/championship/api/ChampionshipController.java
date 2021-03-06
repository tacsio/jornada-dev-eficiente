package br.com.deveficiente.bolaoapi.services.championship.api;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.championship.ChampionshipRepository;
import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.championship.api.model.championship.ChampionshipResponse;
import br.com.deveficiente.bolaoapi.services.championship.api.model.championship.CreateChampionshipRequest;
import br.com.deveficiente.bolaoapi.services.championship.api.model.match.CreateMatchRequest;
import br.com.deveficiente.bolaoapi.services.championship.api.model.match.MatchResponse;
import br.com.deveficiente.bolaoapi.services.team.TeamRepository;
import br.com.deveficiente.bolaoapi.shared.validator.Exists;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/championships")
public class ChampionshipController {

    private final ChampionshipRepository championshipRepository;

    private final TeamRepository teamRepository;

    public ChampionshipController(ChampionshipRepository repository, TeamRepository teamRepository) {
        this.championshipRepository = repository;
        this.teamRepository = teamRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity createChampionship(@RequestBody @Valid CreateChampionshipRequest request) {
        Championship championship = request.toChampionship(teamRepository);
        ChampionshipResponse response = new ChampionshipResponse(this.championshipRepository.save(championship));

        return ResponseEntity.ok(response);
    }


    @PostMapping("/{id}/matches")
    @Transactional
    public ResponseEntity createMatch(@Exists(entityClass = Championship.class) @PathVariable Long id, @RequestBody @Valid CreateMatchRequest request) {
        Championship championship = championshipRepository.findById(id).get();
        Match match = request.toMatch(teamRepository, championship);
        championship.addMatch(match);

        return ResponseEntity.ok(new MatchResponse(match));
    }
}
