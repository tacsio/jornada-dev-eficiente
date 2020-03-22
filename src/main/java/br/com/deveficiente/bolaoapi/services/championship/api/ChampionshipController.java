package br.com.deveficiente.bolaoapi.services.championship.api;

import br.com.deveficiente.bolaoapi.services.championship.ChampionshipRepository;
import br.com.deveficiente.bolaoapi.services.championship.api.model.ChampionshipResponse;
import br.com.deveficiente.bolaoapi.services.championship.api.model.CreateChampionshipRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/championships")
public class ChampionshipController {

    private ChampionshipRepository repository;

    public ChampionshipController(ChampionshipRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreateChampionshipRequest request) {
        ChampionshipResponse response = new ChampionshipResponse(this.repository.save(request.getChampionship()));
        return ResponseEntity.ok(response);
    }
}
