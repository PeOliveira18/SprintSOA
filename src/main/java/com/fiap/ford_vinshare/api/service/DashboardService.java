package com.fiap.ford_vinshare.api.service;

import com.fiap.ford_vinshare.api.model.OrdemServico;
import com.fiap.ford_vinshare.api.model.StatusLead;
import com.fiap.ford_vinshare.api.model.StatusServico;
import com.fiap.ford_vinshare.api.model.Veiculo;
import com.fiap.ford_vinshare.api.dto.VinShareDTO;
import com.fiap.ford_vinshare.api.repository.LeadRetencaoRepository;
import com.fiap.ford_vinshare.api.repository.OrdemServicoRepository;
import com.fiap.ford_vinshare.api.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DashboardService {

    private final VeiculoRepository veiculoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final LeadRetencaoRepository leadRetencaoRepository;

    @Transactional(readOnly = true)
    public VinShareDTO calcularVinShare(Long concessionariaId, String modelo, Integer anoFabricacao) {
        List<Veiculo> veiculos = veiculoRepository.findAll().stream()
                .filter(veiculo -> concessionariaId == null
                        || veiculo.getConcessionariaVenda().getId().equals(concessionariaId))
                .filter(veiculo -> modelo == null
                        || modelo.isBlank()
                        || veiculo.getModelo().equalsIgnoreCase(modelo))
                .filter(veiculo -> anoFabricacao == null
                        || veiculo.getAnoFabricacao().equals(anoFabricacao))
                .toList();

        Set<Long> veiculosFiltrados = veiculos.stream().map(Veiculo::getId).collect(Collectors.toSet());
        long totalVeiculos = veiculos.size();
        long veiculosComServicoRede = veiculos.stream()
                .filter(ordemServicoRepository::existsByVeiculo)
                .count();
        BigDecimal vinShare = totalVeiculos == 0
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(veiculosComServicoRede)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalVeiculos), 2, RoundingMode.HALF_UP);

        long leadsAbertos = leadRetencaoRepository.findByStatus(StatusLead.ABERTO).stream()
                .filter(lead -> veiculosFiltrados.contains(lead.getVeiculo().getId()))
                .count();

        BigDecimal receita = ordemServicoRepository.findByStatus(StatusServico.CONCLUIDA).stream()
                .filter(ordem -> veiculosFiltrados.contains(ordem.getVeiculo().getId()))
                .map(OrdemServico::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new VinShareDTO(
                concessionariaId,
                modelo,
                anoFabricacao,
                totalVeiculos,
                veiculosComServicoRede,
                vinShare,
                leadsAbertos,
                receita
        );
    }
}
