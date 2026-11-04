package com.example.demo.controller;

import com.example.demo.service.ViagemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViagemController {

    private final ViagemService viagemService;

    public ViagemController(ViagemService viagemService) {
        this.viagemService = viagemService;
    }

    @GetMapping("/viagens/preco")
    public double calcularPreco(@RequestParam double distancia) {
        return viagemService.calcularPreco(distancia);
    }
}
