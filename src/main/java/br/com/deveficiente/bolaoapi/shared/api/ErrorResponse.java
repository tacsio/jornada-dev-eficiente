package br.com.deveficiente.bolaoapi.shared.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ErrorResponse {
    @Getter
    private final String error;
}
