package com.fiap.ford_vinshare.api.security;

import com.fiap.ford_vinshare.support.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class SecurityAccessControlTest extends IntegrationTestBase {

    @Test
    void acessarEndpointProtegidoSemTokenRetorna401() throws Exception {
        mockMvc.perform(get("/api/concessionarias"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void acessarEndpointProtegidoComTokenInvalidoRetorna401() throws Exception {
        mockMvc.perform(get("/api/concessionarias")
                        .header(HttpHeaders.AUTHORIZATION, bearer("token.invalido.aqui")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void endpointDeLoginEhPublico() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"admin","password":"admin123"}
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void perfilConcessionariaPodeListarRecursos() throws Exception {
        String token = tokenDealer();

        mockMvc.perform(get("/api/concessionarias")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token)))
                .andExpect(status().isOk());
    }

    @Test
    void perfilConcessionariaNaoPodeCriarRecursos() throws Exception {
        String token = tokenDealer();

        mockMvc.perform(post("/api/concessionarias")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"codigo":"XX999","nome":"Concessionaria Teste","cidade":"Sao Paulo","estado":"SP"}
                                """))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403));
    }

    @Test
    void perfilAnalistaPodeCriarRecursos() throws Exception {
        String token = tokenAnalista();

        mockMvc.perform(post("/api/concessionarias")
                        .header(HttpHeaders.AUTHORIZATION, bearer(token))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"codigo":"AN001","nome":"Concessionaria Analista","cidade":"Sao Paulo","estado":"SP"}
                                """))
                .andExpect(status().isCreated());
    }
}
