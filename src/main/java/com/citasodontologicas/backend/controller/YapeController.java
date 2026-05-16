package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.service.serviceImpl.YapeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/yape")
@RequiredArgsConstructor
public class YapeController {

    private final YapeService yapeService;

    @GetMapping("/qr/{idPago}")
    public String qr(@PathVariable Integer idPago) {
        return yapeService.generarQR(idPago);
    }

    @PostMapping("/confirmar/{idPago}")
    public String confirmar(@PathVariable Integer idPago) {
        yapeService.confirmarPago(idPago);
        return "Pago Yape confirmado";
    }
}