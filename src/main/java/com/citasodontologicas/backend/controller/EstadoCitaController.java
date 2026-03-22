package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.dto.request.EstadoCitaRequest;
import com.citasodontologicas.backend.entity.EstadoCita;
import com.citasodontologicas.backend.service.EstadoCitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-cita")
@RequiredArgsConstructor
@Tag(name = "Estados de Cita", description = "Endpoints para gestión de estados de cita")
public class EstadoCitaController {

    private final EstadoCitaService estadoCitaService;

    @GetMapping
    @Operation(summary = "Listar estados de cita")
    public ResponseEntity<List<EstadoCita>> listar() {
        return ResponseEntity.ok(estadoCitaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener estado de cita por id")
    public ResponseEntity<EstadoCita> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(estadoCitaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Guardar estado de cita")
    public ResponseEntity<EstadoCita> guardar(@Valid @RequestBody EstadoCitaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoCitaService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estado de cita")
    public ResponseEntity<EstadoCita> actualizar(@PathVariable Integer id, @Valid @RequestBody EstadoCitaRequest request) {
        return ResponseEntity.ok(estadoCitaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estado de cita")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        estadoCitaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}