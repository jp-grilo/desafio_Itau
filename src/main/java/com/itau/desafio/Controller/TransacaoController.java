package com.itau.desafio.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itau.desafio.Domain.Transacao;
import com.itau.desafio.Service.TransacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("")
    public ResponseEntity<?> criarTransacao(@Valid @RequestBody Transacao bodyTransacao) {
        try {
            transacaoService.salvarTransacao(bodyTransacao);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body("");
        }
    }

    @GetMapping("")
    public List<Transacao> listarTransacoes() {
        return transacaoService.listarTransacoes();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deletarTransacoes(){
        try{
            transacaoService.deletarTransacoes();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
