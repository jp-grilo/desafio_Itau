package com.itau.desafio.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void limpaTransacoes() throws Exception {
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isNoContent());
    }

    @Test
    void criarTransacao_valida_retornaCreated() throws Exception {
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isCreated());
    }

    @Test
    void criarTransacao_valorNegativo_retornaUnprocessableContent() throws Exception {
        Transacao transacao = new Transacao(-100.0, OffsetDateTime.now());

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    void criarTransacao_dataFutura_retornaUnprocessableContent() throws Exception {
        Transacao transacao = new Transacao(100.0, OffsetDateTime.now().plusMinutes(10));

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isUnprocessableContent());
    }

    @Test
    void criarTransacao_camposVazios_retornaBadRequest() throws Exception {
        Transacao transacaoValorVazio = new Transacao(null, OffsetDateTime.now().plusMinutes(10));

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacaoValorVazio)))
                .andExpect(status().isBadRequest());

        Transacao transacaoDataVazia = new Transacao(100.0, null);

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacaoDataVazia)))
                .andExpect(status().isBadRequest());

        Transacao transacaoVazia = new Transacao(null, null);

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacaoVazia)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criarTransacao_corpoInvalido_retornaBadRequest() throws Exception {
        String corpoInvalido = "{ \"valor\": \"cem\", \"dataHora\": \"agora\" }";

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(corpoInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    void criarTransacao_corpoVazio_retornaBadRequest() throws Exception {
        String corpoVazio = "";

        mockMvc.perform(post("/transacao").contentType(MediaType.APPLICATION_JSON)
                .content(corpoVazio))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarTransacoes_retornaOk() throws Exception {
        mockMvc.perform(get("/transacao")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void listarTransacoes_aposCriarUma_retornaListaComUmElemento() throws Exception {
        Transacao transacao = new Transacao(50.0, OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transacao)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/transacao")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertTrue(json.contains("50.0"));
                });
    }

    @Test
    void listarTransacoes_aposCriarMultiplas_retornaListaComTodos() throws Exception {
        Transacao t1 = new Transacao(10.0, OffsetDateTime.now());
        Transacao t2 = new Transacao(20.0, OffsetDateTime.now());
        Transacao t3 = new Transacao(30.0, OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t2)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t3)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/transacao")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertTrue(json.contains("10.0"));
                    assertTrue(json.contains("20.0"));
                    assertTrue(json.contains("30.0"));
                });
    }

    @Test
    void listarTransacoes_aposCriarEDeletar_retornaListaVazia() throws Exception {
        Transacao t1 = new Transacao(10.0, OffsetDateTime.now());
        Transacao t2 = new Transacao(20.0, OffsetDateTime.now());
        Transacao t3 = new Transacao(30.0, OffsetDateTime.now());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t2)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t3)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/transacao")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/transacao")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertTrue(json.equals("[]"));
                });
    }

    @Test
    void listarTransacoes_transacoesDentroEForaDeIntervalo() throws Exception {
        Transacao t1 = new Transacao(10.0, OffsetDateTime.now());
        Transacao t2 = new Transacao(20.0, OffsetDateTime.now());
        Transacao t3 = new Transacao(30.0, OffsetDateTime.now());
        Transacao t4 = new Transacao(40.0, OffsetDateTime.now().minusMinutes(10));
        Transacao t5 = new Transacao(50.0, OffsetDateTime.now().minusMinutes(10));
        Transacao t6 = new Transacao(60.0, OffsetDateTime.now().minusMinutes(10));

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t2)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t3)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t4)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t5)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(t6)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/transacao")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertTrue(json.contains("10.0"));
                    assertTrue(json.contains("20.0"));
                    assertTrue(json.contains("30.0"));
                    assertFalse(json.contains("40.0"));
                    assertFalse(json.contains("50.0"));
                    assertFalse(json.contains("60.0"));
                });
    }

    @Test
    void deletarTransacoes_retornaNoContent() throws Exception {
        mockMvc.perform(delete("/transacao")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
