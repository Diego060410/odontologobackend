package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RolRequest {

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombreRol;

    private String descripcion;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}