package com.fiap.ford_vinshare.api.service;

import com.fiap.ford_vinshare.api.model.Cliente;
import com.fiap.ford_vinshare.api.dto.ClienteDTO;
import com.fiap.ford_vinshare.api.exception.BusinessException;
import com.fiap.ford_vinshare.api.exception.ResourceNotFoundException;
import com.fiap.ford_vinshare.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public ClienteDTO criar(ClienteDTO request) {
        if (repository.existsByEmailIgnoreCase(request.email())) {
            throw new BusinessException("Ja existe cliente com o email informado");
        }

        Cliente cliente = new Cliente();
        preencher(cliente, request);
        return ClienteDTO.from(repository.save(cliente));
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> listar() {
        return repository.findAll().stream().map(ClienteDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente nao encontrado"));
    }

    @Transactional(readOnly = true)
    public ClienteDTO detalhar(Long id) {
        return ClienteDTO.from(buscarPorId(id));
    }

    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO request) {
        Cliente cliente = buscarPorId(id);
        if (!cliente.getEmail().equalsIgnoreCase(request.email())
                && repository.existsByEmailIgnoreCase(request.email())) {
            throw new BusinessException("Ja existe cliente com o email informado");
        }

        preencher(cliente, request);
        return ClienteDTO.from(cliente);
    }

    private void preencher(Cliente cliente, ClienteDTO request) {
        cliente.setNome(request.nome().trim());
        cliente.setEmail(request.email().trim().toLowerCase());
        cliente.setTelefone(request.telefone().trim());
        cliente.setDocumento(request.documento().trim());
    }
}
