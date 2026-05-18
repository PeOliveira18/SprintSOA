package com.fiap.ford_vinshare.api.service;

import com.fiap.ford_vinshare.api.model.Concessionaria;
import com.fiap.ford_vinshare.api.model.LeadRetencao;
import com.fiap.ford_vinshare.api.model.OrdemServico;
import com.fiap.ford_vinshare.api.model.PrioridadeLead;
import com.fiap.ford_vinshare.api.model.StatusLead;
import com.fiap.ford_vinshare.api.model.TipoLead;
import com.fiap.ford_vinshare.api.model.Veiculo;
import com.fiap.ford_vinshare.api.dto.LeadRetencaoDTO;
import com.fiap.ford_vinshare.api.dto.LeadStatusDTO;
import com.fiap.ford_vinshare.api.exception.ResourceNotFoundException;
import com.fiap.ford_vinshare.api.repository.LeadRetencaoRepository;
import com.fiap.ford_vinshare.api.repository.OrdemServicoRepository;
import com.fiap.ford_vinshare.api.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LeadRetencaoService {

    private static final List<StatusLead> STATUS_EM_ABERTO = List.of(StatusLead.ABERTO, StatusLead.EM_CONTATO);

    private final LeadRetencaoRepository repository;
    private final VeiculoRepository veiculoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final VeiculoService veiculoService;
    private final ConcessionariaService concessionariaService;

    @Transactional
    public LeadRetencaoDTO criar(LeadRetencaoDTO request) {
        Veiculo veiculo = veiculoService.buscarPorVin(request.vin());
        Concessionaria concessionaria = concessionariaService.buscarPorId(request.concessionariaId());

        LeadRetencao lead = new LeadRetencao();
        lead.setVeiculo(veiculo);
        lead.setConcessionaria(concessionaria);
        lead.setTipoLead(request.tipoLead());
        lead.setPrioridade(request.prioridade());
        lead.setMotivo(request.motivo().trim());
        lead.setPrevisaoContato(request.previsaoContato());
        lead.setStatus(StatusLead.ABERTO);

        return LeadRetencaoDTO.from(repository.save(lead));
    }

    @Transactional
    public List<LeadRetencaoDTO> gerarLeads(Integer diasSemServico) {
        int limiteDias = diasSemServico == null ? 180 : diasSemServico;
        LocalDate hoje = LocalDate.now();

        List<LeadRetencao> novosLeads = veiculoRepository.findAll().stream()
                .filter(veiculo -> !repository.existsByVeiculoAndStatusIn(veiculo, STATUS_EM_ABERTO))
                .filter(veiculo -> estaSemServicoHaMaisDe(veiculo, limiteDias, hoje))
                .map(veiculo -> criarLeadAutomatico(veiculo, limiteDias, hoje))
                .toList();

        return repository.saveAll(novosLeads).stream()
                .map(LeadRetencaoDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LeadRetencaoDTO> listar(StatusLead status) {
        List<LeadRetencao> leads = status == null ? repository.findAll() : repository.findByStatus(status);
        return leads.stream().map(LeadRetencaoDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public LeadRetencaoDTO detalhar(Long id) {
        return LeadRetencaoDTO.from(buscarPorId(id));
    }

    @Transactional
    public LeadRetencaoDTO atualizarStatus(Long id, LeadStatusDTO request) {
        LeadRetencao lead = buscarPorId(id);
        lead.setStatus(request.status());
        return LeadRetencaoDTO.from(lead);
    }

    @Transactional(readOnly = true)
    public LeadRetencao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead de retencao nao encontrado"));
    }

    private boolean estaSemServicoHaMaisDe(Veiculo veiculo, int limiteDias, LocalDate hoje) {
        LocalDate referencia = ordemServicoRepository.findTopByVeiculoOrderByDataServicoDesc(veiculo)
                .map(OrdemServico::getDataServico)
                .orElse(veiculo.getDataCompra());
        return !referencia.plusDays(limiteDias).isAfter(hoje);
    }

    private LeadRetencao criarLeadAutomatico(Veiculo veiculo, int limiteDias, LocalDate hoje) {
        LocalDate referencia = ordemServicoRepository.findTopByVeiculoOrderByDataServicoDesc(veiculo)
                .map(OrdemServico::getDataServico)
                .orElse(veiculo.getDataCompra());
        long dias = ChronoUnit.DAYS.between(referencia, hoje);

        LeadRetencao lead = new LeadRetencao();
        lead.setVeiculo(veiculo);
        lead.setConcessionaria(veiculo.getConcessionariaVenda());
        lead.setTipoLead(TipoLead.REVISAO_ATRASADA);
        lead.setPrioridade(dias >= 365 ? PrioridadeLead.ALTA : PrioridadeLead.MEDIA);
        lead.setStatus(StatusLead.ABERTO);
        lead.setMotivo("Cliente sem servico registrado na rede oficial ha " + dias + " dias; limite configurado: " + limiteDias + " dias");
        lead.setPrevisaoContato(hoje.plusDays(3));
        return lead;
    }
}
