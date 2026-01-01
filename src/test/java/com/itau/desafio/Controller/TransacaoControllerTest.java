package com.itau.desafio.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import com.itau.desafio.Domain.Transacao;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarTransacao_valida_retornaCreated() throws Exception {
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isCreated());
    }

    @Test
    void criarTransacao_valorNegativo_retornaBadRequest() throws Exception {
        Transacao transacao = new Transacao(-100.0, OffsetDateTime.now());

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    void criarTransacao_dataFutura_retornaBadRequest() throws Exception{
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now().plusMinutes(10));

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    void criarTransacao_valorVazio_retornaBadRequest() throws Exception{
        Transacao transacao = new Transacao(null, OffsetDateTime.now().plusMinutes(10));

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criarTransacao_horaVazia_retornaBadRequest() throws Exception{
        Transacao transacao = new Transacao(100.0, null);

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criarTransacao_camposVazios_retornaBadRequest() throws Exception{
        Transacao transacao = new Transacao(null, null);

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criarTransacao_corpoInvalido_retornaBadRequest() throws Exception{
        String corpoInvalido = "{ \"valor\": \"cem\", \"dataHora\": \"agora\" }";

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(corpoInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criarTransacao_corpoVazio_retornaBadRequest() throws Exception{
        String corpoVazio = "";

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(corpoVazio))
                .andExpect(status().isBadRequest());
    }
}
