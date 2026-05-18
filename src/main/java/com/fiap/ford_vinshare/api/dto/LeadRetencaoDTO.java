package com.fiap.ford_vinshare.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.ford_vinshare.api.model.LeadRetencao;
import com.fiap.ford_vinshare.api.model.PrioridadeLead;
import com.fiap.ford_vinshare.api.model.StatusLead;
import com.fiap.ford_vinshare.api.model.TipoLead;
import com.fiap.ford_vinshare.api.validation.ValidVin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LeadRetencaoDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank(message = "VIN e obrigatorio")
        @ValidVin
        String vin,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String clienteNome,

        @NotNull(message = "Concessionaria e obrigatoria")
        Long concessionariaId,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String concessionariaNome,

        @NotNull(message = "Tipo de lead e obrigatorio")
        TipoLead tipoLead,

        @NotNull(message = "Prioridade e obrigatoria")
        PrioridadeLead prioridade,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        StatusLead status,

        @NotBlank(message = "Motivo e obrigatorio")
        @Size(max = 255, message = "Motivo deve ter no maximo 255 caracteres")
        String motivo,

        @NotNull(message = "Previsao de contato e obrigatoria")
        @FutureOrPresent(message = "Previsao de contato deve ser hoje ou uma data futura")
        LocalDate previsaoContato,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime criadoEm,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime atualizadoEm
) {
    public static LeadRetencaoDTO from(LeadRetencao lead) {
        return new LeadRetencaoDTO(
                lead.getId(),
                lead.getVeiculo().getVin(),
                lead.getVeiculo().getCliente().getNome(),
                lead.getConcessionaria().getId(),
                lead.getConcessionaria().getNome(),
                lead.getTipoLead(),
                lead.getPrioridade(),
                lead.getStatus(),
                lead.getMotivo(),
                lead.getPrevisaoContato(),
                lead.getCriadoEm(),
                lead.getAtualizadoEm()
        );
    }
}
