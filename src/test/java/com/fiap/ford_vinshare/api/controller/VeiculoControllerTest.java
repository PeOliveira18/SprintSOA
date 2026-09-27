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
class VeiculoControllerTest extends IntegrationTestBase {

    @Test
    void criarVeiculoComSucessoRetorna201() throws Exception {
        String token = tokenAdmin();

        mockMvc.perform(post("/api/veiculos")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "vin":"1FTFW1ET5DFA12345",
                                  "marca":"Ford",
                                  "modelo":"F-150",
                                  "anoFabricacao":2025,
                                  "dataCompra":"2025-01-10",
                                  "quilometragemAtual":0,
                                  "garantiaAtiva":true,
                                  "clienteId":1,
                                  "concessionariaVendaId":1
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.vin").value("1FTFW1ET5DFA12345"))
                .andExpect(jsonPath("$.clienteNome").isNotEmpty());
    }

    @Test
    void criarVeiculoComVinInvalidoRetorna400() throws Exception {
        String token = tokenAdmin();

        mockMvc.perform(post("/api/veiculos")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "vin":"VIN-CURTO-INVALIDO",
                                  "marca":"Ford",
                                  "modelo":"F-150",
                                  "anoFabricacao":2025,
                                  "dataCompra":"2025-01-10",
                                  "quilometragemAtual":0,
                                  "garantiaAtiva":true,
                                  "clienteId":1,
                                  "concessionariaVendaId":1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.vin").exists());
    }

    @Test
    void criarVeiculoComVinDuplicadoRetorna409() throws Exception {
        String token = tokenAdmin();

        mockMvc.perform(post("/api/veiculos")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "vin":"9BFZH55L9P8123456",
                                  "marca":"Ford",
                                  "modelo":"Ranger",
                                  "anoFabricacao":2023,
                                  "dataCompra":"2023-05-20",
                                  "quilometragemAtual":28000,
                                  "garantiaAtiva":true,
                                  "clienteId":1,
                                  "concessionariaVendaId":1
                                }
                                """))
                .andExpect(status().isConflict());
    }

    @Test
    void buscarVeiculoInexistenteRetorna404() throws Exception {
        String token = tokenAdmin();

        mockMvc.perform(get("/api/veiculos/{vin}", "9BFZH55L9P9999999")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token)))
                .andExpect(status().isNotFound());
    }

    @Test
    void listarVeiculosFiltrandoPorModeloRetorna200() throws Exception {
        String token = tokenDealer();

        mockMvc.perform(get("/api/veiculos").param("modelo", "Ranger")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").value("Ranger"));
    }
}
