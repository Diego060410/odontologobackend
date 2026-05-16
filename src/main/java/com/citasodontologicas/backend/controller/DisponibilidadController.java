package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.service.DisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
@RequiredArgsConstructor
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping
    public List<Object> obtenerDisponibilidad(
            @RequestParam Integer odontologo,
            @RequestParam String fecha
    ) {

        return disponibilidadService.obtenerHorasDisponibles(
                odontologo,
                LocalDate.parse(fecha)
        );
    }
}