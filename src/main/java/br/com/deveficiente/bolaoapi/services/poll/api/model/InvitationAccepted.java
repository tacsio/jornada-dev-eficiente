package br.com.deveficiente.bolaoapi.services.poll.api.model;

import br.com.deveficiente.bolaoapi.services.poll.Invitation;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class InvitationAccepted {
    @Getter
    private Long pollId;

    @Getter
    private String invitedEmail;

    public InvitationAccepted(Invitation invitation) {
        this.pollId = invitation.getPoll().getId();
        this.invitedEmail = invitation.getEmail();
    }
}
