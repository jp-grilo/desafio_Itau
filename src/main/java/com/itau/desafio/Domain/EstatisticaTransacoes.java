package com.itau.desafio.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstatisticaTransacoes {
    private long count;
    private double sum;
    private double avg;
    private double max;
    private double min;
}
