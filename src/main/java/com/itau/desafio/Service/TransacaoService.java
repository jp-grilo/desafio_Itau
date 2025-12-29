package com.itau.desafio.Service;

import org.springframework.stereotype.Service;

import com.itau.desafio.Domain.Transacao;

@Service
public class TransacaoService {

    public void salvarTransacao(Transacao bodyTransacao) {
        if (bodyTransacao.getValor() < 0) {
            throw new IllegalArgumentException("Valor da transação não pode ser negativo.");
        }
        if (bodyTransacao.getDataHora().isAfter(java.time.OffsetDateTime.now())) {
            throw new IllegalArgumentException("Data e hora da transação não podem ser futuras.");
        }
    }
}
