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

    // Editando mensagem padrão da exceção de validação
    @NotNull(message = "O valor da transação não pode ser nulo.")
    private Double valor;

    @NotNull(message = "A data e hora da transação não pode ser nula.")
    private OffsetDateTime dataHora;
}
