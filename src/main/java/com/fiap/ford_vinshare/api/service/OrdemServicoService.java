package com.fiap.ford_vinshare.api.service;

import com.fiap.ford_vinshare.api.model.Concessionaria;
import com.fiap.ford_vinshare.api.model.OrdemServico;
import com.fiap.ford_vinshare.api.model.StatusServico;
import com.fiap.ford_vinshare.api.model.Veiculo;
import com.fiap.ford_vinshare.api.dto.OrdemServicoDTO;
import com.fiap.ford_vinshare.api.exception.BusinessException;
import com.fiap.ford_vinshare.api.exception.ResourceNotFoundException;
import com.fiap.ford_vinshare.api.repository.OrdemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrdemServicoService {

    private final OrdemServicoRepository repository;
    private final VeiculoService veiculoService;
    private final ConcessionariaService concessionariaService;

    @Transactional
    public OrdemServicoDTO criar(OrdemServicoDTO request) {
        Veiculo veiculo = veiculoService.buscarPorVin(request.vin());
        Concessionaria concessionaria = concessionariaService.buscarPorId(request.concessionariaId());

        if (request.quilometragem() < veiculo.getQuilometragemAtual()) {
            throw new BusinessException("Quilometragem da ordem nao pode ser menor que a quilometragem atual do veiculo");
        }

        OrdemServico ordem = new OrdemServico();
        ordem.setVeiculo(veiculo);
        ordem.setConcessionaria(concessionaria);
        ordem.setTipoServico(request.tipoServico());
        ordem.setDataServico(request.dataServico());
        ordem.setQuilometragem(request.quilometragem());
        ordem.setValor(request.valor());
        ordem.setStatus(request.status());
        ordem.setObservacao(request.observacao());

        if (request.status() == StatusServico.CONCLUIDA) {
            veiculo.setQuilometragemAtual(request.quilometragem());
        }

        return OrdemServicoDTO.from(repository.save(ordem));
    }

    @Transactional(readOnly = true)
    public List<OrdemServicoDTO> listar(String vin, StatusServico status) {
        if (vin != null && !vin.isBlank()) {
            return repository.findByVeiculoVinIgnoreCaseOrderByDataServicoDesc(vin).stream()
                    .map(OrdemServicoDTO::from)
                    .toList();
        }
        if (status != null) {
            return repository.findByStatus(status).stream()
                    .map(OrdemServicoDTO::from)
                    .toList();
        }
        return repository.findAll().stream().map(OrdemServicoDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public OrdemServicoDTO detalhar(Long id) {
        return OrdemServicoDTO.from(buscarPorId(id));
    }

    @Transactional(readOnly = true)
    public OrdemServico buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem de servico nao encontrada"));
    }
}
