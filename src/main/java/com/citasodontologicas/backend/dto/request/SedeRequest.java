package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Data
public class SedeRequest {

    @NotBlank(message = "El nombre de la sede es obligatorio")
    private String nombreSede;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    private String telefono;

    @Email(message = "El correo no es válido")
    private String correo;

    @NotNull(message = "La hora de apertura es obligatoria")
    private LocalTime horaApertura;

    @NotNull(message = "La hora de cierre es obligatoria")
    private LocalTime horaCierre;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}