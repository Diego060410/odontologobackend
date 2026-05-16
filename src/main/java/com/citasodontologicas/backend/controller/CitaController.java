package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.dto.request.CitaRequest;
import com.citasodontologicas.backend.entity.Cita;
import com.citasodontologicas.backend.service.CitaService;
import com.citasodontologicas.backend.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@Tag(name = "Citas", description = "Endpoints para gestión de citas")
public class CitaController {

    private final CitaService citaService;

    private final EmailService emailService;

    @GetMapping
    @Operation(summary = "Listar citas")
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(citaService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cita por id")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(citaService.obtenerPorId(id));
    }

    // --- NUEVO MÉTODO PARA FILTRAR POR ODONTÓLOGO ---
    @GetMapping("/odontologo/{id}")
    @Operation(summary = "Listar citas por id de odontólogo")
    public ResponseEntity<List<Cita>> listarPorOdontologo(@PathVariable Integer id) {
        // Asegúrate de tener este método creado en tu citaService
        return ResponseEntity.ok(citaService.listarPorOdontologo(id));
    }

    @PostMapping
    @Operation(summary = "Guardar cita")
    public ResponseEntity<Cita> guardar(@Valid @RequestBody CitaRequest request) {

        // ✅ GUARDAR CITA
        Cita nuevaCita = citaService.guardar(request);

        // ✅ OBTENER DATOS DEL PACIENTE
        String correoPaciente = nuevaCita.getPaciente()
                .getUsuario()
                .getCorreo();

        String nombrePaciente = nuevaCita.getPaciente()
                .getUsuario()
                .getNombres();

        // ✅ DATOS DE LA CITA
        String fecha = nuevaCita.getFecha().toString();

        String hora = nuevaCita.getHoraInicio().toString();

        String odontologo = nuevaCita.getOdontologo()
                .getUsuario()
                .getNombres();

        String sede = nuevaCita.getSede()
                .getNombreSede();

        String consultorio = nuevaCita.getConsultorio()
                .getNombreConsultorio();

        // ✅ ENVIAR CORREO
        emailService.enviarCorreoCita(
                correoPaciente,
                nombrePaciente,
                fecha,
                hora,
                odontologo,
                sede,
                consultorio
        );

        // ✅ RESPUESTA
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevaCita);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cita")
    public ResponseEntity<Cita> actualizar(@PathVariable Integer id, @Valid @RequestBody CitaRequest request) {
        return ResponseEntity.ok(citaService.actualizar(id, request));
    }

    // Agrega este método dentro de tu CitaController
    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar solo el estado de una cita")
    public ResponseEntity<Cita> actualizarEstado(@PathVariable Integer id, @RequestBody java.util.Map<String, Object> payload) {
        // Extraemos el idEstado del JSON enviado: { "idEstado": 3 }
        Integer nuevoEstado = (Integer) payload.get("idEstado");
        return ResponseEntity.ok(citaService.actualizarEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cita")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}