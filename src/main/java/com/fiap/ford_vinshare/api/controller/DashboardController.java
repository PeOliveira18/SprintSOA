package com.fiap.ford_vinshare.api.controller;

import com.fiap.ford_vinshare.api.dto.VinShareDTO;
import com.fiap.ford_vinshare.api.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    @GetMapping("/vin-share")
    public VinShareDTO calcularVinShare(
            @RequestParam(required = false) Long concessionariaId,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Integer anoFabricacao
    ) {
        return service.calcularVinShare(concessionariaId, modelo, anoFabricacao);
    }
}
