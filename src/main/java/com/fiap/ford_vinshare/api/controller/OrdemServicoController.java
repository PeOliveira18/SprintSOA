package com.fiap.ford_vinshare.api.controller;

import com.fiap.ford_vinshare.api.model.StatusServico;
import com.fiap.ford_vinshare.api.dto.OrdemServicoDTO;
import com.fiap.ford_vinshare.api.service.OrdemServicoService;
import com.fiap.ford_vinshare.api.validation.ValidVin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoService service;

    @PostMapping
    public ResponseEntity<OrdemServicoDTO> criar(@RequestBody @Valid OrdemServicoDTO request) {
        OrdemServicoDTO response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/ordens-servico/" + response.id())).body(response);
    }

    @GetMapping
    public List<OrdemServicoDTO> listar(
            @RequestParam(required = false) @ValidVin String vin,
            @RequestParam(required = false) StatusServico status
    ) {
        return service.listar(vin, status);
    }

    @GetMapping("/{id}")
    public OrdemServicoDTO detalhar(@PathVariable Long id) {
        return service.detalhar(id);
    }
}
