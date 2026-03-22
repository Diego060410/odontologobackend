package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificacionRequest {

    @NotNull(message = "El id de la cita es obligatorio")
    private Integer idCita;

    @NotBlank(message = "El medio es obligatorio")
    private String medio;

    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatario;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    private String estado;
}