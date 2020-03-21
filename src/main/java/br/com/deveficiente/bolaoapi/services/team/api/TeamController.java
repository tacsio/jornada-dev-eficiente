package br.com.deveficiente.bolaoapi.services.team.api;

import br.com.deveficiente.bolaoapi.services.team.TeamRepository;
import br.com.deveficiente.bolaoapi.services.team.api.model.CreateTeamRequest;
import br.com.deveficiente.bolaoapi.services.team.api.model.TeamResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private TeamRepository repository;

    public TeamController(TeamRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreateTeamRequest request) {
        TeamResponse response = new TeamResponse(repository.save(request.getTeam()));

        return ResponseEntity.ok(response);
    }
}
