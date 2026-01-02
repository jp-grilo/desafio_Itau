package com.itau.desafio.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itau.desafio.Domain.Transacao;

@Service
public class TransacaoService {

    private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(TransacaoService.class);

    public List<Transacao> listarTransacoes() {
        logger.info("Listando transações do último minuto");

        OffsetDateTime limite = OffsetDateTime.now().minusSeconds(60);

        return transacoes.stream()
                .filter(t -> t.getDataHora().isAfter(limite))
                .toList();
    }

    public void salvarTransacao(Transacao bodyTransacao) {
        logger.info("Salvando transação: valor={}, dataHora={}", bodyTransacao.getValor(), bodyTransacao.getDataHora());

        if (bodyTransacao.getValor() < 0) {
            logger.warn("Tentativa de salvar transação com valor negativo: {}", bodyTransacao.getValor());
            throw new IllegalArgumentException("Valor da transação não pode ser negativo.");
        }
        if (bodyTransacao.getDataHora().isAfter(java.time.OffsetDateTime.now())) {
            logger.warn("Tentativa de salvar transação com data futura: {}", bodyTransacao.getDataHora());
            throw new IllegalArgumentException("Data e hora da transação não podem ser futuras.");
        }
        transacoes.add(bodyTransacao);
    }

    public void deletarTransacoes() {
        logger.info("Deletando todas as transações");

        transacoes.clear();
    }
}
