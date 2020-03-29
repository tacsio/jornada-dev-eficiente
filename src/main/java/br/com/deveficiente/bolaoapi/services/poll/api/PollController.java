package br.com.deveficiente.bolaoapi.services.poll.api;

import br.com.deveficiente.bolaoapi.services.championship.ChampionshipRepository;
import br.com.deveficiente.bolaoapi.services.poll.Poll;
import br.com.deveficiente.bolaoapi.services.poll.PollRepository;
import br.com.deveficiente.bolaoapi.services.poll.api.model.CreatePollRequest;
import br.com.deveficiente.bolaoapi.services.poll.api.model.PollResponse;
import br.com.deveficiente.bolaoapi.services.user.User;
import br.com.deveficiente.bolaoapi.services.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/polls")
public class PollController {

    private final PollRepository pollRepository;
    private final ChampionshipRepository championshipRepository;
    private final InvitationController invitationController;

    //TODO: remove when add spring security features
    private final UserRepository userRepository;

    public PollController(PollRepository pollRepository, ChampionshipRepository championshipRepository, InvitationController invitationController, UserRepository userRepository) {
        this.pollRepository = pollRepository;
        this.championshipRepository = championshipRepository;
        this.invitationController = invitationController;
        this.userRepository = userRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreatePollRequest request) {
        //TODO: remove when add spring security features
        User loggedUser = userRepository.findById(1l).get();
        Poll poll = request.toPoll(loggedUser, championshipRepository);

        ResponseEntity response = ResponseEntity.ok(new PollResponse(pollRepository.save(poll)));
        invitationController.sendInvitationsByEmail(poll.getInvitations());

        return response;
    }
}
