package com.fiap.ford_vinshare.api.service;

import com.fiap.ford_vinshare.api.model.Concessionaria;
import com.fiap.ford_vinshare.api.dto.ConcessionariaDTO;
import com.fiap.ford_vinshare.api.exception.BusinessException;
import com.fiap.ford_vinshare.api.exception.ResourceNotFoundException;
import com.fiap.ford_vinshare.api.repository.ConcessionariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ConcessionariaService {

    private final ConcessionariaRepository repository;

    @Transactional
    public ConcessionariaDTO criar(ConcessionariaDTO request) {
        if (repository.existsByCodigoIgnoreCase(request.codigo())) {
            throw new BusinessException("Ja existe concessionaria com o codigo informado");
        }

        Concessionaria concessionaria = new Concessionaria();
        preencher(concessionaria, request);
        return ConcessionariaDTO.from(repository.save(concessionaria));
    }

    @Transactional(readOnly = true)
    public List<ConcessionariaDTO> listar(Boolean apenasAtivas) {
        List<Concessionaria> concessionarias = Boolean.TRUE.equals(apenasAtivas)
                ? repository.findByAtivoTrue()
                : repository.findAll();
        return concessionarias.stream().map(ConcessionariaDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public Concessionaria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concessionaria nao encontrada"));
    }

    @Transactional(readOnly = true)
    public ConcessionariaDTO detalhar(Long id) {
        return ConcessionariaDTO.from(buscarPorId(id));
    }

    @Transactional
    public ConcessionariaDTO atualizar(Long id, ConcessionariaDTO request) {
        Concessionaria concessionaria = buscarPorId(id);
        if (!concessionaria.getCodigo().equalsIgnoreCase(request.codigo())
                && repository.existsByCodigoIgnoreCase(request.codigo())) {
            throw new BusinessException("Ja existe concessionaria com o codigo informado");
        }

        preencher(concessionaria, request);
        return ConcessionariaDTO.from(concessionaria);
    }

    @Transactional
    public void desativar(Long id) {
        Concessionaria concessionaria = buscarPorId(id);
        concessionaria.setAtivo(false);
    }

    private void preencher(Concessionaria concessionaria, ConcessionariaDTO request) {
        concessionaria.setCodigo(request.codigo().trim().toUpperCase());
        concessionaria.setNome(request.nome().trim());
        concessionaria.setCidade(request.cidade().trim());
        concessionaria.setEstado(request.estado().trim().toUpperCase());
        concessionaria.setAtivo(request.ativo() == null || request.ativo());
    }
}
