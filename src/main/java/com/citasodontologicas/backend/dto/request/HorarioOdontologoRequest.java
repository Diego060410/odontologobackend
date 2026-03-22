package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class HorarioOdontologoRequest {

    @NotNull(message = "El id del odontólogo es obligatorio")
    private Integer idOdontologo;

    @NotNull(message = "El id de la sede es obligatorio")
    private Integer idSede;

    @NotBlank(message = "El día de la semana es obligatorio")
    private String diaSemana;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}