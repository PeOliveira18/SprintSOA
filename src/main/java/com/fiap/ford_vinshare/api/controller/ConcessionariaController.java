package com.fiap.ford_vinshare.api.controller;

import com.fiap.ford_vinshare.api.dto.ConcessionariaDTO;
import com.fiap.ford_vinshare.api.service.ConcessionariaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/concessionarias")
public class ConcessionariaController {

    private final ConcessionariaService service;

    @PostMapping
    public ResponseEntity<ConcessionariaDTO> criar(@RequestBody @Valid ConcessionariaDTO request) {
        ConcessionariaDTO response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/concessionarias/" + response.id())).body(response);
    }

    @GetMapping
    public List<ConcessionariaDTO> listar(@RequestParam(required = false) Boolean apenasAtivas) {
        return service.listar(apenasAtivas);
    }

    @GetMapping("/{id}")
    public ConcessionariaDTO detalhar(@PathVariable Long id) {
        return service.detalhar(id);
    }

    @PutMapping("/{id}")
    public ConcessionariaDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ConcessionariaDTO request
    ) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        service.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
