package br.com.deveficiente.bolaoapi.services.poll.api.model.shot;

import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.championship.Scoreboard;
import br.com.deveficiente.bolaoapi.services.poll.Participant;
import br.com.deveficiente.bolaoapi.services.poll.Shot;
import br.com.deveficiente.bolaoapi.services.user.User;
import br.com.deveficiente.bolaoapi.shared.validator.Exists;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@ToString
public class CreateShotRequest {

    @Getter
    @Exists(entityClass = Match.class)
    private Long matchId;

    @Getter
    @Exists(entityClass = Participant.class)
    private Long participantId;

    @Getter
    @PositiveOrZero
    private Integer homeTeamGoals;

    @Getter
    @PositiveOrZero
    private Integer visitingTeamGoals;

    @Getter
    @NotNull
    private Boolean doubled;

    public Shot toShot(EntityManager manager, User loggedUser) {
        Participant participant = manager.find(Participant.class, participantId);
        Match match = manager.find(Match.class, matchId);

        Scoreboard scoreboard = new Scoreboard(homeTeamGoals, visitingTeamGoals);
        Shot shot = new Shot(participant, match, scoreboard, doubled);

        Assert.isTrue(participant.sameAccount(loggedUser), "Shame!. You should not make shots to other accounts.");
        return shot;
    }
}


