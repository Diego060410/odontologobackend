package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EstadoCitaRequest {

    @NotBlank(message = "El nombre del estado es obligatorio")
    private String nombreEstado;

    private String descripcion;
}