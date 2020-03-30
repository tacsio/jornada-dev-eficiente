package br.com.deveficiente.bolaoapi.services.poll.api;

import br.com.deveficiente.bolaoapi.services.poll.Invitation;
import br.com.deveficiente.bolaoapi.services.poll.InvitationRepository;
import br.com.deveficiente.bolaoapi.services.poll.Poll;
import br.com.deveficiente.bolaoapi.services.poll.PollRepository;
import br.com.deveficiente.bolaoapi.services.poll.api.model.InvitationAccepted;
import br.com.deveficiente.bolaoapi.services.user.User;
import br.com.deveficiente.bolaoapi.services.user.UserRepository;
import br.com.deveficiente.bolaoapi.shared.api.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/invitations")
public class InvitationEmailController {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final PollRepository pollRepository;

    public InvitationEmailController(InvitationRepository invitationRepository, UserRepository userRepository, PollRepository pollRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.pollRepository = pollRepository;
    }

    @Transactional
    @GetMapping
    public ResponseEntity process(@RequestParam String key, @RequestParam Boolean accept, UriComponentsBuilder uriBuilder) {
        Optional<Invitation> invitation = invitationRepository.findByKeyAndExpirationAfter(key, LocalDateTime.now());

        //check invitation
        if (!invitation.isPresent()) return invalidInvitation();

        //check acceptance
        if (!accept) return declineInvitation(invitation);

        //check user
        String invitedUserLogin = invitation.get().getEmail();
        Optional<User> invitedUser = userRepository.findByLogin(invitedUserLogin);

        if (!invitedUser.isPresent()) return userNotExists(uriBuilder);

        return ResponseEntity.ok(acceptInvitation(invitation, invitedUser));
    }

    private ResponseEntity invalidInvitation() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("invitation link not exists or expired."));
    }

    private ResponseEntity userNotExists(UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path("/users").build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).build();
    }

    private ResponseEntity declineInvitation(Optional<Invitation> invitation) {
        invitation.get().decline();
        this.invitationRepository.save(invitation.get());
        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    private InvitationAccepted acceptInvitation(Optional<Invitation> invitation, Optional<User> invitedUser) {
        Poll invitationPoll = invitation.get().getPoll();
        invitationPoll.addPartipant(invitedUser.get());
        invitation.get().accept();
        pollRepository.save(invitationPoll);

        return new InvitationAccepted(invitation.get());
    }
}
