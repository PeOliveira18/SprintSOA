package com.fiap.ford_vinshare.api.controller;

import com.fiap.ford_vinshare.api.dto.VeiculoDTO;
import com.fiap.ford_vinshare.api.service.VeiculoService;
import com.fiap.ford_vinshare.api.validation.ValidVin;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    @PostMapping
    public ResponseEntity<VeiculoDTO> criar(@RequestBody @Valid VeiculoDTO request) {
        VeiculoDTO response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/veiculos/" + response.vin())).body(response);
    }

    @GetMapping
    public List<VeiculoDTO> listar(@RequestParam(required = false) String modelo) {
        return service.listar(modelo);
    }

    @GetMapping("/{vin}")
    public VeiculoDTO detalhar(@PathVariable @ValidVin String vin) {
        return service.detalhar(vin);
    }

    @PutMapping("/{vin}")
    public VeiculoDTO atualizar(
            @PathVariable @ValidVin String vin,
            @RequestBody @Valid VeiculoDTO request
    ) {
        return service.atualizar(vin, request);
    }
}
