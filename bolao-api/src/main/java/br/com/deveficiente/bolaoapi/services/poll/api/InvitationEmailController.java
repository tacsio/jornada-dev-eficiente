package br.com.deveficiente.bolaoapi.services.poll.api;

import br.com.deveficiente.bolaoapi.services.poll.Invitation;
import br.com.deveficiente.bolaoapi.services.poll.InvitationRepository;
import br.com.deveficiente.bolaoapi.services.poll.api.model.invitation.InvitationAccepted;
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

    public InvitationEmailController(InvitationRepository invitationRepository, UserRepository userRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @GetMapping("/accept")
    public ResponseEntity process(@RequestParam String key, UriComponentsBuilder uriBuilder) {
        Optional<Invitation> invitation = invitationRepository.findByKeyAndExpirationAfter(key, LocalDateTime.now());

        //validate invitation
        if (!invitation.isPresent()) return invalidInvitation();

        //validate user
        String invitedUserLogin = invitation.get().getEmail();
        Optional<User> invitedUser = userRepository.findByLogin(invitedUserLogin);
        if (!invitedUser.isPresent()) return userNotExists(uriBuilder);

        invitation.get().accept(invitedUser.get());
        return ResponseEntity.ok(new InvitationAccepted(invitation.get()));
    }

    @Transactional
    @GetMapping("/deny")
    public ResponseEntity deny(@RequestParam String key) {
        Optional<Invitation> invitation = invitationRepository.findByKeyAndExpirationAfter(key, LocalDateTime.now());

        //validate invitation
        if (!invitation.isPresent()) return invalidInvitation();
        invitation.get().decline();

        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    private ResponseEntity invalidInvitation() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("invitation link not exists or expired."));
    }

    private ResponseEntity userNotExists(UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path("/users").build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        ErrorResponse error = new ErrorResponse("User not exists. Create user first, them accept invitation");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(headers).body(error);
    }
}
