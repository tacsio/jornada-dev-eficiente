package br.com.deveficiente.bolaoapi.services.poll;

import lombok.Getter;
import lombok.ToString;

@ToString
public class RankingResult {
    @Getter
    private Participant participant;

    @Getter
    private Long totalScore;

    public RankingResult(Participant participant, Long totalScore) {
        this.participant = participant;
        this.totalScore = totalScore;
    }
}
