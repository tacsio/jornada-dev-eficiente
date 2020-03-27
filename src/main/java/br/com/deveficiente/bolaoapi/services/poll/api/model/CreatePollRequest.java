package br.com.deveficiente.bolaoapi.services.poll.api.model;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.poll.Invitation;
import br.com.deveficiente.bolaoapi.services.poll.Poll;
import br.com.deveficiente.bolaoapi.services.user.User;
import br.com.deveficiente.bolaoapi.shared.validator.EmailCollection;
import br.com.deveficiente.bolaoapi.shared.validator.Exists;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CreatePollRequest {

    @Getter
    @EmailCollection
    @Size(min = 1, message = "at least 1 email must be informed.")
    private final Set<String> invitations = new HashSet<>();

    @Getter
    @NotNull
    @Positive
    @Exists(entityClass = Championship.class, entityField = "id")
    private Long championshipId;

    public Poll toPoll(Long loggedUser) {
        User user = new User(loggedUser);
        Championship championship = new Championship(championshipId);

        Poll poll = new Poll(user, championship);
        toInvitations(poll).stream().forEach(poll::addInvitation);

        return poll;
    }

    private Set<Invitation> toInvitations(Poll poll) {
        return this.invitations.stream()
                .map(email ->  new Invitation(email, poll))
                .collect(Collectors.toSet());
    }
}
