package com.itau.desafio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.desafio.Domain.Transacao;
import com.itau.desafio.Service.TransacaoService;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    
    @Autowired
    private TransacaoService transacaoService;


    @PostMapping("")
    public String criarTransacao(@RequestBody Transacao bodyTransacao) {
        return transacaoService.salvarTransacao(bodyTransacao);
    }
}
