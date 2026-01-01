package com.itau.desafio.Domain;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transacao {
    @NotNull

    private Double valor;

    @NotNull

    private OffsetDateTime dataHora;
}
