package br.com.deveficiente.bolaoapi.services.poll.api;

import br.com.deveficiente.bolaoapi.services.poll.Poll;
import br.com.deveficiente.bolaoapi.services.poll.PollRepository;
import br.com.deveficiente.bolaoapi.services.poll.api.model.CreatePollRequest;
import br.com.deveficiente.bolaoapi.services.poll.api.model.PollResponse;
import br.com.deveficiente.bolaoapi.services.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/polls")
public class PollController {

    private final PollRepository pollRepository;

    @Autowired
    private EntityManager em;

    public PollController(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreatePollRequest request) {
        Long userId = 1L; //TODO: change to spring security
        Poll poll = request.toPoll(userId);

        pollRepository.save(poll);
        em.refresh(poll);
        ResponseEntity response = ResponseEntity.ok(new PollResponse(poll));

        return response;
    }

}
