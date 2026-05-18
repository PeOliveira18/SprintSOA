package com.fiap.ford_vinshare.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.ford_vinshare.api.model.Veiculo;
import com.fiap.ford_vinshare.api.validation.ValidVin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record VeiculoDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank(message = "VIN e obrigatorio")
        @ValidVin
        String vin,

        @NotBlank(message = "Marca e obrigatoria")
        @Size(max = 40, message = "Marca deve ter no maximo 40 caracteres")
        String marca,

        @NotBlank(message = "Modelo e obrigatorio")
        @Size(max = 80, message = "Modelo deve ter no maximo 80 caracteres")
        String modelo,

        @NotNull(message = "Ano de fabricacao e obrigatorio")
        @Min(value = 1990, message = "Ano de fabricacao deve ser maior ou igual a 1990")
        @Max(value = 2030, message = "Ano de fabricacao deve ser menor ou igual a 2030")
        Integer anoFabricacao,

        @NotNull(message = "Data de compra e obrigatoria")
        @PastOrPresent(message = "Data de compra nao pode estar no futuro")
        LocalDate dataCompra,

        @NotNull(message = "Quilometragem atual e obrigatoria")
        @PositiveOrZero(message = "Quilometragem atual nao pode ser negativa")
        Integer quilometragemAtual,

        @NotNull(message = "Garantia ativa e obrigatoria")
        Boolean garantiaAtiva,

        @NotNull(message = "Cliente e obrigatorio")
        Long clienteId,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String clienteNome,

        @NotNull(message = "Concessionaria de venda e obrigatoria")
        Long concessionariaVendaId,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String concessionariaVendaNome
) {
    public static VeiculoDTO from(Veiculo veiculo) {
        return new VeiculoDTO(
                veiculo.getId(),
                veiculo.getVin(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAnoFabricacao(),
                veiculo.getDataCompra(),
                veiculo.getQuilometragemAtual(),
                veiculo.getGarantiaAtiva(),
                veiculo.getCliente().getId(),
                veiculo.getCliente().getNome(),
                veiculo.getConcessionariaVenda().getId(),
                veiculo.getConcessionariaVenda().getNome()
        );
    }
}
