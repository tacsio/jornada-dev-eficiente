package br.com.deveficiente.bolaoapi.services.championship;

import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

public class ChampionshipMatches {

    private final Set<Match> matches = new HashSet<>();

    public ChampionshipMatches(Set<Match> matches) {
        this.matches.clear();
        this.matches.addAll(matches);
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
