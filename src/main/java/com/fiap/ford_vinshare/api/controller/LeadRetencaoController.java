package com.fiap.ford_vinshare.api.controller;

import com.fiap.ford_vinshare.api.model.StatusLead;
import com.fiap.ford_vinshare.api.dto.LeadRetencaoDTO;
import com.fiap.ford_vinshare.api.dto.LeadStatusDTO;
import com.fiap.ford_vinshare.api.service.LeadRetencaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/leads-retencao")
public class LeadRetencaoController {

    private final LeadRetencaoService service;

    @PostMapping
    public ResponseEntity<LeadRetencaoDTO> criar(@RequestBody @Valid LeadRetencaoDTO request) {
        LeadRetencaoDTO response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/leads-retencao/" + response.id())).body(response);
    }

    @PostMapping("/geracao-automatica")
    public List<LeadRetencaoDTO> gerarLeads(
            @RequestParam(defaultValue = "180") @Min(value = 30, message = "Dias sem servico deve ser no minimo 30") Integer diasSemServico
    ) {
        return service.gerarLeads(diasSemServico);
    }

    @GetMapping
    public List<LeadRetencaoDTO> listar(@RequestParam(required = false) StatusLead status) {
        return service.listar(status);
    }

    @GetMapping("/{id}")
    public LeadRetencaoDTO detalhar(@PathVariable Long id) {
        return service.detalhar(id);
    }

    @PatchMapping("/{id}/status")
    public LeadRetencaoDTO atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid LeadStatusDTO request
    ) {
        return service.atualizarStatus(id, request);
    }
}
