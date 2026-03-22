package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OdontologoRequest {

    @NotNull(message = "El id del usuario es obligatorio")
    private Integer idUsuario;

    @NotNull(message = "El id de la especialidad es obligatorio")
    private Integer idEspecialidad;

    @NotBlank(message = "El número de colegiatura es obligatorio")
    private String numeroColegiatura;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}