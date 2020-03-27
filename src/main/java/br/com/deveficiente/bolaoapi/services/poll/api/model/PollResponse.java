package br.com.deveficiente.bolaoapi.services.poll.api.model;

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
    private Long id;
    @Getter
    private String owner;
    @Getter
    private String championship;
    @Getter
    private LocalDateTime createdAt;
    @Getter
    private Set<String> invites;

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
