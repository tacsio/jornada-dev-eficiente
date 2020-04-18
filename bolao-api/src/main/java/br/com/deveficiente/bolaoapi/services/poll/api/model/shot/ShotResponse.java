package br.com.deveficiente.bolaoapi.services.poll.api.model.shot;

import br.com.deveficiente.bolaoapi.services.poll.Shot;
import lombok.Getter;
import lombok.ToString;

@ToString
public class ShotResponse {
    @Getter
    private String user;
    @Getter
    private String shot;
    @Getter
    private boolean doubled;

    public ShotResponse(Shot shot) {

        this.user = shot.getParticipant().getLogin();
        this.doubled = shot.getDoubled();
        this.shot = String.format("%s (%s) x (%s) %s",
                shot.getMatch().getHomeTeam().getName(),
                shot.getScoreboard().getHomeTeamGoals(),
                shot.getScoreboard().getVisitingTeamGoals(),
                shot.getMatch().getVisitingTeam().getName());
    }
}
