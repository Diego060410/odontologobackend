package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CitaRequest {

    @NotNull(message = "El id del paciente es obligatorio")
    private Integer idPaciente;

    @NotNull(message = "El id del odontólogo es obligatorio")
    private Integer idOdontologo;

    @NotNull(message = "El id de la sede es obligatorio")
    private Integer idSede;

    private Integer idConsultorio;

    @NotNull(message = "El id del estado de cita es obligatorio")
    private Integer idEstadoCita;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    private String motivo;

    private String observaciones;

    private Integer registradoPor;
}