package com.fiap.ford_vinshare.api.controller;

import com.fiap.ford_vinshare.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class ConcessionariaControllerTest extends IntegrationTestBase {

    @Test
    void listarConcessionariasRetorna200ComItensDoSeed() throws Exception {
        String token = tokenAdmin();

        mockMvc.perform(get("/api/concessionarias")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigo").exists());
    }

    @Test
    void criarConcessionariaComSucessoRetorna201ComLocation() throws Exception {
        String token = tokenAdmin();

        mockMvc.perform(post("/api/concessionarias")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"codigo":"MG001","nome":"Ford BH Service","cidade":"Belo Horizonte","estado":"MG"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.codigo").value("MG001"))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    @Test
    void criarConcessionariaComCodigoDuplicadoRetorna409() throws Exception {
        String token = tokenAdmin();

        mockMvc.perform(post("/api/concessionarias")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"codigo":"SP001","nome":"Duplicada","cidade":"Sao Paulo","estado":"SP"}
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Business rule"));
    }

    @Test
    void criarConcessionariaComCamposInvalidosRetorna400() throws Exception {
        String token = tokenAdmin();

        mockMvc.perform(post("/api/concessionarias")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"codigo":"","nome":"","cidade":"","estado":"SPX"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.codigo").exists())
                .andExpect(jsonPath("$.fields.estado").exists());
    }

    @Test
    void buscarConcessionariaInexistenteRetorna404() throws Exception {
        String token = tokenAdmin();

        mockMvc.perform(get("/api/concessionarias/{id}", 9999L)
                        .header(HttpHeaders.AUTHORIZATION, bearer(token)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not found"));
    }
}
