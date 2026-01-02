package com.itau.desafio.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itau.desafio.Domain.EstatisticaTransacoes;
import com.itau.desafio.Domain.Transacao;

@Service
public class EstatisticaService {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoService.class);

    @Autowired
    private TransacaoService transacaoService;

    public EstatisticaTransacoes estatisticasUltimoMinuto() {
        logger.info("Calculando estatísticas das transações do último minuto");
        long inicio = System.nanoTime();
        var transacoes = transacaoService.listarTransacoes();

        var stats = transacoes.stream()
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();

        long fim = System.nanoTime();
        logger.info("Estatísticas calculadas em {} ms", (fim - inicio) / 1000000);
        
        return new EstatisticaTransacoes(
                stats.getCount(),
                stats.getSum(),
                stats.getCount() == 0 ? 0.0 : stats.getAverage(),
                stats.getCount() == 0 ? 0.0 : stats.getMax(),
                stats.getCount() == 0 ? 0.0 : stats.getMin());
    }

}
