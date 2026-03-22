package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.dto.request.SedeRequest;
import com.citasodontologicas.backend.entity.Sede;
import com.citasodontologicas.backend.service.SedeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sedes")
@RequiredArgsConstructor
@Tag(name = "Sedes", description = "Endpoints para gestión de sedes")
public class SedeController {

    private final SedeService sedeService;

    @GetMapping
    @Operation(summary = "Listar sedes")
    public ResponseEntity<List<Sede>> listar() {
        return ResponseEntity.ok(sedeService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sede por id")
    public ResponseEntity<Sede> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(sedeService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Guardar sede")
    public ResponseEntity<Sede> guardar(@Valid @RequestBody SedeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sedeService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar sede")
    public ResponseEntity<Sede> actualizar(@PathVariable Integer id, @Valid @RequestBody SedeRequest request) {
        return ResponseEntity.ok(sedeService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sede")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        sedeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}