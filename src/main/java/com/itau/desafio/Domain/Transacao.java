package com.itau.desafio.Domain;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transacao {
    private Double valor;
    private OffsetDateTime dataHora;
}
