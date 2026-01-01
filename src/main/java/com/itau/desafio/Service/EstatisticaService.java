package com.itau.desafio.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itau.desafio.Domain.EstatisticaTransacoes;
import com.itau.desafio.Domain.Transacao;

@Service
public class EstatisticaService {

    @Autowired
    private TransacaoService transacaoService;

    public EstatisticaTransacoes estatisticasUltimoMinuto() {

        var transacoes = transacaoService.listarTransacoes();

        var stats = transacoes.stream()
                .mapToDouble(Transacao::getValor)
                .summaryStatistics();

        return new EstatisticaTransacoes(
                stats.getCount(),
                stats.getSum(),
                stats.getCount() == 0 ? 0.0 : stats.getAverage(),
                stats.getCount() == 0 ? 0.0 : stats.getMax(),
                stats.getCount() == 0 ? 0.0 : stats.getMin());
    }

}
