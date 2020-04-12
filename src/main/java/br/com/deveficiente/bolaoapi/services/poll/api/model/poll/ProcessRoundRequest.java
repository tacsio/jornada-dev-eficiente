package br.com.deveficiente.bolaoapi.services.poll.api.model.poll;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Positive;

@ToString
public class ProcessRoundRequest {

    @Getter
    @Positive
    private Integer round;

}
