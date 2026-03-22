package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConsultorioRequest {

    @NotNull(message = "El id de la sede es obligatorio")
    private Integer idSede;

    @NotBlank(message = "El nombre del consultorio es obligatorio")
    private String nombreConsultorio;

    private String piso;

    private String descripcion;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}