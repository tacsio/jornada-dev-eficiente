package br.com.deveficiente.bolaoapi.services.championship;

import org.springframework.util.Assert;

import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class ChampionshipMatches {

    private final Set<Match> matches = new HashSet<>();

    public ChampionshipMatches(Set<Match> matches) {
        this.matches.clear();
        this.matches.addAll(matches);
    }

    public Stream<Shot> shotsByRound(@Positive Integer round) {
        Stream<Match> matchStream = (round > 0) ?
                matchesByRound(round) :
                matches.stream();

        return matchStream.map(Match::getShots).flatMap(Collection::stream);
    }

    private Stream<Match> matchesByRound(@Positive Integer round) {
        return matches.stream()
                .filter(m -> Objects.equals(m.getRound(), round));
    }

    public void validateNewMatch(Match newMatch) {
        Assert.isTrue(!hasConflictInRound(newMatch), "A team cannot play 2 matches in same round.");
    }

    private boolean hasConflictInRound(Match match) {
        boolean overlay = matches.stream()
                .anyMatch(m -> m.hasConflict(match));

        return overlay;
    }

}
