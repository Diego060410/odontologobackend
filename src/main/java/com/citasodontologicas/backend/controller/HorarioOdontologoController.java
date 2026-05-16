package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.dto.request.HorarioOdontologoRequest;
import com.citasodontologicas.backend.entity.HorarioOdontologo;
import com.citasodontologicas.backend.service.HorarioOdontologoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios-odontologos")
@RequiredArgsConstructor
@Tag(name = "Horarios Odontólogos", description = "Endpoints para gestión de horarios de odontólogos")
public class HorarioOdontologoController {

    private final HorarioOdontologoService horarioOdontologoService;

    @GetMapping
    @Operation(summary = "Listar horarios")
    public ResponseEntity<List<HorarioOdontologo>> listar() {
        return ResponseEntity.ok(horarioOdontologoService.listar());
    }

    @GetMapping("/odontologo/{idOdontologo}")
    public ResponseEntity<List<HorarioOdontologo>> listarPorOdontologo(
            @PathVariable Integer idOdontologo
    ) {
        return ResponseEntity.ok(
                horarioOdontologoService.listarPorOdontologo(idOdontologo)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener horario por id")
    public ResponseEntity<HorarioOdontologo> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(horarioOdontologoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Guardar horario")
    public ResponseEntity<HorarioOdontologo> guardar(@Valid @RequestBody HorarioOdontologoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioOdontologoService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar horario")
    public ResponseEntity<HorarioOdontologo> actualizar(@PathVariable Integer id, @Valid @RequestBody HorarioOdontologoRequest request) {
        return ResponseEntity.ok(horarioOdontologoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar horario")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        horarioOdontologoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}