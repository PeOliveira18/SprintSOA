package com.fiap.ford_vinshare.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.List;

public record LoginDTO(
        @NotBlank(message = "Usuario e obrigatorio")
        String username,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "Senha e obrigatoria")
        String password,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String tokenType,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String accessToken,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Instant expiresAt,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        List<String> roles
) {
}
