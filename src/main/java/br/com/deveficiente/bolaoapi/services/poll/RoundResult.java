package br.com.deveficiente.bolaoapi.services.poll;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"poll_id", "participant_id", "round"}, name = "uk_round_result"))
public class RoundResult {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @OneToOne
    private Participant participant;

    @Getter
    @ManyToOne
    private Poll poll;

    @Getter
    private Integer round;

    @Getter
    private Integer score;

    @Getter
    private LocalDateTime timestamp;

    protected RoundResult() {
    }

    public RoundResult(Participant participant, Poll poll, Integer round, Integer score, LocalDateTime timestamp) {
        this.participant = participant;
        this.poll = poll;
        this.round = round;
        this.score = score;
        this.timestamp = timestamp;
    }
}
