package com.fiap.ford_vinshare.api.dto;

import java.math.BigDecimal;

public record VinShareDTO(
        Long concessionariaId,
        String modelo,
        Integer anoFabricacao,
        long totalVeiculos,
        long veiculosComServicoRede,
        BigDecimal vinSharePercentual,
        long leadsAbertos,
        BigDecimal receitaServicosConcluidos
) {
}
