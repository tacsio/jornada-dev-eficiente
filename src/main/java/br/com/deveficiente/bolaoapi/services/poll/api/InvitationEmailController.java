package br.com.deveficiente.bolaoapi.services.poll.api;

import br.com.deveficiente.bolaoapi.services.poll.Invitation;
import br.com.deveficiente.bolaoapi.services.poll.InvitationRepository;
import br.com.deveficiente.bolaoapi.services.poll.api.model.InvitationAccepted;
import br.com.deveficiente.bolaoapi.shared.api.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/invitations")
public class InvitationEmailController {

    private final InvitationRepository invitationRepository;

    public InvitationEmailController(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Transactional
    @GetMapping
    public ResponseEntity process(@RequestParam String key) {
        Optional<Invitation> invitation = invitationRepository.findByKeyAndExpirationAfter(key, LocalDateTime.now());

        if (!invitation.isPresent())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("invitation link expired."));

        return ResponseEntity.ok(new InvitationAccepted(invitation.get()));
    }
}
