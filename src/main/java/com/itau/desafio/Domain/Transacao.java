package com.itau.desafio.Domain;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transacao {
    @NotNull
    @Positive
    private Double valor;

    @NotNull
    @PastOrPresent
    private OffsetDateTime dataHora;
}
