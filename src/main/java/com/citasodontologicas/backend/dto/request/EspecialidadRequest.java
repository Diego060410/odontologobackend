package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EspecialidadRequest {

    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    private String nombreEspecialidad;

    private String descripcion;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}