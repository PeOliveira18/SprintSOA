package com.fiap.ford_vinshare.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.ford_vinshare.api.model.Concessionaria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ConcessionariaDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank(message = "Codigo e obrigatorio")
        @Size(max = 20, message = "Codigo deve ter no maximo 20 caracteres")
        String codigo,

        @NotBlank(message = "Nome e obrigatorio")
        @Size(max = 120, message = "Nome deve ter no maximo 120 caracteres")
        String nome,

        @NotBlank(message = "Cidade e obrigatoria")
        @Size(max = 80, message = "Cidade deve ter no maximo 80 caracteres")
        String cidade,

        @NotBlank(message = "Estado e obrigatorio")
        @Pattern(regexp = "^[A-Za-z]{2}$", message = "Estado deve ser a sigla UF com 2 letras")
        String estado,

        Boolean ativo
) {
    public static ConcessionariaDTO from(Concessionaria concessionaria) {
        return new ConcessionariaDTO(
                concessionaria.getId(),
                concessionaria.getCodigo(),
                concessionaria.getNome(),
                concessionaria.getCidade(),
                concessionaria.getEstado(),
                concessionaria.getAtivo()
        );
    }
}
