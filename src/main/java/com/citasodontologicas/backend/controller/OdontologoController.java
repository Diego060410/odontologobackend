package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.dto.request.OdontologoRequest;
import com.citasodontologicas.backend.entity.Odontologo;
import com.citasodontologicas.backend.service.OdontologoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/odontologos")
@RequiredArgsConstructor
@Tag(name = "Odontólogos", description = "Endpoints para gestión de odontólogos")
public class OdontologoController {

    private final OdontologoService odontologoService;

    @GetMapping
    @Operation(summary = "Listar odontólogos")
    public ResponseEntity<List<Odontologo>> listar() {
        return ResponseEntity.ok(odontologoService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener odontólogo por id")
    public ResponseEntity<Odontologo> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(odontologoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Guardar odontólogo")
    public ResponseEntity<Odontologo> guardar(@Valid @RequestBody OdontologoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar odontólogo")
    public ResponseEntity<Odontologo> actualizar(@PathVariable Integer id, @Valid @RequestBody OdontologoRequest request) {
        return ResponseEntity.ok(odontologoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar odontólogo")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        odontologoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}