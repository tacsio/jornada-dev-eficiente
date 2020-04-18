package br.com.deveficiente.bolaoapi.services.poll.api.model.poll;

import br.com.deveficiente.bolaoapi.services.poll.Invitation;
import br.com.deveficiente.bolaoapi.services.poll.Poll;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
public class PollResponse {

    @Getter
    private final Long id;
    @Getter
    private final String owner;
    @Getter
    private final String championship;
    @Getter
    private final LocalDateTime createdAt;
    @Getter
    private final Set<String> invites;

    public PollResponse(Poll poll) {
        this.id = poll.getId();
        this.owner = poll.getOwner().getLogin();
        this.championship = poll.getChampionship().getName();
        this.createdAt = poll.getCreatedAt();
        this.invites = poll.getInvitations()
                .stream()
                .map(Invitation::getEmail)
                .collect(Collectors.toSet());
    }
}
