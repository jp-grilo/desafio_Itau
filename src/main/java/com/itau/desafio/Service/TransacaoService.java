package com.itau.desafio.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import com.itau.desafio.Domain.Transacao;

@Service
public class TransacaoService {

    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();

    public List<Transacao> listarTransacoes(){

        OffsetDateTime limite = OffsetDateTime.now().minusSeconds(60);

        return transacoes.stream()
                .filter(t -> t.getDataHora().isAfter(limite))
                .toList();
    }
    
    public void salvarTransacao(Transacao bodyTransacao) {
        if (bodyTransacao.getValor() < 0) {
            throw new IllegalArgumentException("Valor da transação não pode ser negativo.");
        }
        if (bodyTransacao.getDataHora().isAfter(java.time.OffsetDateTime.now())) {
            throw new IllegalArgumentException("Data e hora da transação não podem ser futuras.");
        }
        transacoes.add(bodyTransacao);
    }

    public void deletarTransacoes() {
        transacoes.clear();
    }
}
