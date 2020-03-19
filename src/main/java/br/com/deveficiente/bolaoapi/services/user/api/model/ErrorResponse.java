package br.com.deveficiente.bolaoapi.services.user.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ErrorResponse {
    @Getter
    private String error;
}
