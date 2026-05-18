package com.fiap.ford_vinshare.api.dto;

import com.fiap.ford_vinshare.api.model.StatusLead;
import jakarta.validation.constraints.NotNull;

public record LeadStatusDTO(
        @NotNull(message = "Status e obrigatorio")
        StatusLead status
) {
}
