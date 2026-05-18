package com.fiap.ford_vinshare.api.service;

import com.fiap.ford_vinshare.api.model.Cliente;
import com.fiap.ford_vinshare.api.model.Concessionaria;
import com.fiap.ford_vinshare.api.model.Veiculo;
import com.fiap.ford_vinshare.api.dto.VeiculoDTO;
import com.fiap.ford_vinshare.api.exception.BusinessException;
import com.fiap.ford_vinshare.api.exception.ResourceNotFoundException;
import com.fiap.ford_vinshare.api.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VeiculoService {

    private final VeiculoRepository repository;
    private final ClienteService clienteService;
    private final ConcessionariaService concessionariaService;

    @Transactional
    public VeiculoDTO criar(VeiculoDTO request) {
        if (repository.existsByVinIgnoreCase(request.vin())) {
            throw new BusinessException("Ja existe veiculo cadastrado com o VIN informado");
        }

        Veiculo veiculo = new Veiculo();
        preencher(veiculo, request);
        return VeiculoDTO.from(repository.save(veiculo));
    }

    @Transactional(readOnly = true)
    public List<VeiculoDTO> listar(String modelo) {
        List<Veiculo> veiculos = modelo == null || modelo.isBlank()
                ? repository.findAll()
                : repository.findByModeloContainingIgnoreCase(modelo);
        return veiculos.stream().map(VeiculoDTO::from).toList();
    }

    @Transactional(readOnly = true)
    public Veiculo buscarPorVin(String vin) {
        return repository.findByVinIgnoreCase(vin)
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo nao encontrado"));
    }

    @Transactional(readOnly = true)
    public VeiculoDTO detalhar(String vin) {
        return VeiculoDTO.from(buscarPorVin(vin));
    }

    @Transactional
    public VeiculoDTO atualizar(String vin, VeiculoDTO request) {
        Veiculo veiculo = buscarPorVin(vin);
        if (!veiculo.getVin().equalsIgnoreCase(request.vin())
                && repository.existsByVinIgnoreCase(request.vin())) {
            throw new BusinessException("Ja existe veiculo cadastrado com o VIN informado");
        }

        preencher(veiculo, request);
        return VeiculoDTO.from(veiculo);
    }

    private void preencher(Veiculo veiculo, VeiculoDTO request) {
        Cliente cliente = clienteService.buscarPorId(request.clienteId());
        Concessionaria concessionaria = concessionariaService.buscarPorId(request.concessionariaVendaId());

        veiculo.setVin(request.vin().trim().toUpperCase());
        veiculo.setMarca(request.marca().trim());
        veiculo.setModelo(request.modelo().trim());
        veiculo.setAnoFabricacao(request.anoFabricacao());
        veiculo.setDataCompra(request.dataCompra());
        veiculo.setQuilometragemAtual(request.quilometragemAtual());
        veiculo.setGarantiaAtiva(request.garantiaAtiva());
        veiculo.setCliente(cliente);
        veiculo.setConcessionariaVenda(concessionaria);
    }
}
