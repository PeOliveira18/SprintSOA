package com.fiap.ford_vinshare.api.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private final UserDetails usuario = User.withUsername("analista")
            .password("irrelevante")
            .roles("ANALISTA")
            .build();

    @Test
    void geraTokenValidoComTresPartesEValidaComSucesso() {
        JwtService jwtService = new JwtService("segredo-de-teste-bem-longo-para-hmac", 120);

        String token = jwtService.generateToken(usuario);

        assertThat(token.split("\\.")).hasSize(3);
        assertThat(jwtService.extractUsername(token)).isEqualTo("analista");
        assertThat(jwtService.extractRoles(token)).containsExactly("ROLE_ANALISTA");
        assertThat(jwtService.isTokenValid(token, usuario)).isTrue();
    }

    @Test
    void tokenGeradoParaOutroUsuarioEhInvalido() {
        JwtService jwtService = new JwtService("segredo-de-teste-bem-longo-para-hmac", 120);
        UserDetails outroUsuario = User.withUsername("admin").password("x").roles("ADMIN").build();

        String token = jwtService.generateToken(usuario);

        assertThat(jwtService.isTokenValid(token, outroUsuario)).isFalse();
    }

    @Test
    void tokenExpiradoEhInvalido() {
        JwtService jwtService = new JwtService("segredo-de-teste-bem-longo-para-hmac", 0);

        String token = jwtService.generateToken(usuario);

        assertThat(jwtService.isTokenValid(token, usuario)).isFalse();
    }

    @Test
    void tokenComAssinaturaAdulteradaEhInvalido() {
        JwtService jwtService = new JwtService("segredo-de-teste-bem-longo-para-hmac", 120);
        String token = jwtService.generateToken(usuario);
        String[] parts = token.split("\\.");
        String tokenAdulterado = parts[0] + "." + parts[1] + "." + "assinatura-forjada";

        assertThat(jwtService.isTokenValid(tokenAdulterado, usuario)).isFalse();
    }

    @Test
    void tokenAssinadoComSegredoDiferenteEhInvalido() {
        JwtService emissor = new JwtService("segredo-A-bem-longo-para-hmac", 120);
        JwtService verificador = new JwtService("segredo-B-bem-longo-para-hmac", 120);

        String token = emissor.generateToken(usuario);

        assertThat(verificador.isTokenValid(token, usuario)).isFalse();
    }
}
