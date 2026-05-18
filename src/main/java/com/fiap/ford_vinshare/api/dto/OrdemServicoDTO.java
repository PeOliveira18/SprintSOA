package com.fiap.ford_vinshare.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.ford_vinshare.api.model.OrdemServico;
import com.fiap.ford_vinshare.api.model.StatusServico;
import com.fiap.ford_vinshare.api.model.TipoServico;
import com.fiap.ford_vinshare.api.validation.ValidVin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrdemServicoDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank(message = "VIN e obrigatorio")
        @ValidVin
        String vin,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String modelo,

        @NotNull(message = "Concessionaria e obrigatoria")
        Long concessionariaId,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String concessionariaNome,

        @NotNull(message = "Tipo de servico e obrigatorio")
        TipoServico tipoServico,

        @NotNull(message = "Data do servico e obrigatoria")
        @PastOrPresent(message = "Data do servico nao pode estar no futuro")
        LocalDate dataServico,

        @NotNull(message = "Quilometragem e obrigatoria")
        @PositiveOrZero(message = "Quilometragem nao pode ser negativa")
        Integer quilometragem,

        @NotNull(message = "Valor e obrigatorio")
        @PositiveOrZero(message = "Valor nao pode ser negativo")
        BigDecimal valor,

        @NotNull(message = "Status e obrigatorio")
        StatusServico status,

        @Size(max = 255, message = "Observacao deve ter no maximo 255 caracteres")
        String observacao
) {
    public static OrdemServicoDTO from(OrdemServico ordemServico) {
        return new OrdemServicoDTO(
                ordemServico.getId(),
                ordemServico.getVeiculo().getVin(),
                ordemServico.getVeiculo().getModelo(),
                ordemServico.getConcessionaria().getId(),
                ordemServico.getConcessionaria().getNome(),
                ordemServico.getTipoServico(),
                ordemServico.getDataServico(),
                ordemServico.getQuilometragem(),
                ordemServico.getValor(),
                ordemServico.getStatus(),
                ordemServico.getObservacao()
        );
    }
}
