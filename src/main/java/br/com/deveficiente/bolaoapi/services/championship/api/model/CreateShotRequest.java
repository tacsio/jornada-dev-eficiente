package br.com.deveficiente.bolaoapi.services.championship.api.model;

import br.com.deveficiente.bolaoapi.services.championship.Match;
import br.com.deveficiente.bolaoapi.services.championship.MatchRepository;
import br.com.deveficiente.bolaoapi.services.championship.Scoreboard;
import br.com.deveficiente.bolaoapi.services.championship.Shot;
import br.com.deveficiente.bolaoapi.services.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

@ToString
public class CreateShotRequest {

    @Getter
    @PositiveOrZero
    private Integer homeTeamGoals;

    @Getter
    @PositiveOrZero
    private Integer visitingTeamGoals;

    @Getter
    @NotNull
    private Boolean doubled;

    public Shot toShot(User participant, Match match, MatchRepository matchRepository) {
        if (doubled) validateDoubleShot(match, participant, matchRepository);

        Scoreboard scoreboard = new Scoreboard(homeTeamGoals, visitingTeamGoals);
        Shot shot = new Shot(participant, match, scoreboard, doubled);

        return shot;
    }

    private void validateDoubleShot(Match match, User participant, MatchRepository matchRepository) {
        Integer round = match.getRound();
        Long championshipId = match.getChampionship().getId();
        Long participantId = participant.getId();

        Optional<Match> doubledMatch = matchRepository.findMatchWithDoubledShot(championshipId, round, participantId);
        if (doubledMatch.isPresent()) {
            throw new IllegalArgumentException("Only one doubled shot is permitted per round.");
        }
    }
}


