package com.itau.desafio.Controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
public class EstatisticasControllerTest {

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
    void retornaEstatisticas_vazia() throws Exception {
        mockMvc.perform(get("/estatisticas"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertTrue(json.contains("count\":0"));
                    assertTrue(json.contains("sum\":0"));
                    assertTrue(json.contains("avg\":0"));
                    assertTrue(json.contains("max\":0"));
                    assertTrue(json.contains("min\":0"));
                });
    }

    @Test
    void retornaEstatisticas_preenchida() throws Exception {
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

        mockMvc.perform(get("/estatisticas"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertTrue(json.contains("count\":3"));
                    assertTrue(json.contains("sum\":60"));
                    assertTrue(json.contains("avg\":20"));
                    assertTrue(json.contains("max\":30"));
                    assertTrue(json.contains("min\":10"));
                });
    }

}
