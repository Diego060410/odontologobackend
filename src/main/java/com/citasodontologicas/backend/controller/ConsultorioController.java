package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.dto.request.ConsultorioRequest;
import com.citasodontologicas.backend.entity.Consultorio;
import com.citasodontologicas.backend.service.ConsultorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultorios")
@RequiredArgsConstructor
@Tag(name = "Consultorios", description = "Endpoints para gestión de consultorios")
public class ConsultorioController {

    private final ConsultorioService consultorioService;

    @GetMapping
    @Operation(summary = "Listar consultorios")
    public ResponseEntity<List<Consultorio>> listar() {
        return ResponseEntity.ok(consultorioService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener consultorio por id")
    public ResponseEntity<Consultorio> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(consultorioService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Guardar consultorio")
    public ResponseEntity<Consultorio> guardar(@Valid @RequestBody ConsultorioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultorioService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar consultorio")
    public ResponseEntity<Consultorio> actualizar(@PathVariable Integer id, @Valid @RequestBody ConsultorioRequest request) {
        return ResponseEntity.ok(consultorioService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar consultorio")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        consultorioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}