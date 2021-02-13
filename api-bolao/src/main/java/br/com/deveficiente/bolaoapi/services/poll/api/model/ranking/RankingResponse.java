package br.com.deveficiente.bolaoapi.services.poll.api.model.ranking;

import br.com.deveficiente.bolaoapi.services.poll.RankingResult;
import lombok.Getter;
import lombok.ToString;

@ToString
public class RankingResponse {

    @Getter
    private String participant;

    @Getter
    private Long score;

    public RankingResponse(RankingResult result) {
        this.participant = result.getParticipant().getLogin();
        this.score = result.getTotalScore();
    }
}