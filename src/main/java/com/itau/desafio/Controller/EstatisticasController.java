package com.itau.desafio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.desafio.Domain.EstatisticaTransacoes;
import com.itau.desafio.Service.EstatisticaService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {
    @Autowired
    private EstatisticaService estatisticaService;

    @GetMapping("")
    public EstatisticaTransacoes obterEstatisticas() {
        return estatisticaService.estatisticasUltimoMinuto();
    }
}
