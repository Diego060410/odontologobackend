package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.dto.request.NotificacionRequest;
import com.citasodontologicas.backend.entity.Notificacion;
import com.citasodontologicas.backend.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@Tag(name = "Notificaciones", description = "Endpoints para gestión de notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    @Operation(summary = "Listar notificaciones")
    public ResponseEntity<List<Notificacion>> listar() {
        return ResponseEntity.ok(notificacionService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por id")
    public ResponseEntity<Notificacion> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(notificacionService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Guardar notificación")
    public ResponseEntity<Notificacion> guardar(@Valid @RequestBody NotificacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar notificación")
    public ResponseEntity<Notificacion> actualizar(@PathVariable Integer id,
                                                   @Valid @RequestBody NotificacionRequest request) {
        return ResponseEntity.ok(notificacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar notificación")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}