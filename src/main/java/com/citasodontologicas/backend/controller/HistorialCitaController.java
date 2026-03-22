package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.dto.request.HistorialCitaRequest;
import com.citasodontologicas.backend.entity.HistorialCita;
import com.citasodontologicas.backend.service.HistorialCitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-citas")
@RequiredArgsConstructor
@Tag(name = "Historial de Citas", description = "Endpoints para gestión del historial de citas")
public class HistorialCitaController {

    private final HistorialCitaService historialCitaService;

    @GetMapping
    @Operation(summary = "Listar historial de citas")
    public ResponseEntity<List<HistorialCita>> listar() {
        return ResponseEntity.ok(historialCitaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener historial por id")
    public ResponseEntity<HistorialCita> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(historialCitaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Guardar historial de cita")
    public ResponseEntity<HistorialCita> guardar(@Valid @RequestBody HistorialCitaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historialCitaService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar historial de cita")
    public ResponseEntity<HistorialCita> actualizar(@PathVariable Integer id,
                                                    @Valid @RequestBody HistorialCitaRequest request) {
        return ResponseEntity.ok(historialCitaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar historial de cita")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        historialCitaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}