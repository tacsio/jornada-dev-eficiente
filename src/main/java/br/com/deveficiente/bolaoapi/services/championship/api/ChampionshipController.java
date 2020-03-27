package br.com.deveficiente.bolaoapi.services.championship.api;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.championship.ChampionshipRepository;
import br.com.deveficiente.bolaoapi.services.championship.api.model.ChampionshipResponse;
import br.com.deveficiente.bolaoapi.services.championship.api.model.CreateChampionshipRequest;
import br.com.deveficiente.bolaoapi.services.team.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/championships")
public class ChampionshipController {

    private ChampionshipRepository championshipRepository;

    private TeamRepository teamRepository;

    public ChampionshipController(ChampionshipRepository repository, TeamRepository teamRepository) {
        this.championshipRepository = repository;
        this.teamRepository = teamRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid CreateChampionshipRequest request) {
        Championship championship = request.getChampionship(teamRepository);
        ChampionshipResponse response = new ChampionshipResponse(this.championshipRepository.save(championship));

        return ResponseEntity.ok(response);
    }
}
