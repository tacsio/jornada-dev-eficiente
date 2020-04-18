package br.com.deveficiente.bolaoapi.services.poll.api.model.invitation;

import br.com.deveficiente.bolaoapi.services.poll.Invitation;
import lombok.Getter;
import lombok.ToString;

@ToString
public class InvitationAccepted {
    @Getter
    private final Long pollId;

    @Getter
    private final String invitedEmail;

    public InvitationAccepted(Invitation invitation) {
        this.pollId = invitation.getPoll().getId();
        this.invitedEmail = invitation.getEmail();
    }
}
