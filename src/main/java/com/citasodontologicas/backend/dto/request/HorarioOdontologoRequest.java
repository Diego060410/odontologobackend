package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HorarioOdontologoRequest {

    @NotNull(message = "El id del odontólogo es obligatorio")
    private Integer idOdontologo;

    @NotNull(message = "El id de la sede es obligatorio")
    private Integer idSede;

    // 🔥 ESTA ES LA LÍNEA QUE FALTABA Y CAUSABA EL ERROR:
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotBlank(message = "El día de la semana es obligatorio")
    private String diaSemana;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @NotNull(message = "El id del consultorio es obligatorio")
    private Integer idConsultorio;
}