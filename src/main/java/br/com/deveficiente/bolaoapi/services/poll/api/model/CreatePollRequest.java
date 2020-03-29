package br.com.deveficiente.bolaoapi.services.poll.api.model;

import br.com.deveficiente.bolaoapi.services.championship.Championship;
import br.com.deveficiente.bolaoapi.services.championship.ChampionshipRepository;
import br.com.deveficiente.bolaoapi.services.poll.Poll;
import br.com.deveficiente.bolaoapi.services.user.User;
import br.com.deveficiente.bolaoapi.shared.validator.Exists;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class CreatePollRequest {

    @Getter
    @Size(min = 1, message = "at least 1 email must be informed.")
    private final Set<@Email(message = "check invitation emails.") String> invitations = new HashSet<>();

    @Getter
    @NotNull
    @Positive
    @Exists(entityClass = Championship.class, entityField = "id")
    private Long championshipId;

    public Poll toPoll(User loggedUser, ChampionshipRepository championshipRepository) {
        Championship championship = championshipRepository.findById(championshipId).get();
        Poll poll = new Poll(loggedUser, championship, invitations);

        return poll;
    }
}
