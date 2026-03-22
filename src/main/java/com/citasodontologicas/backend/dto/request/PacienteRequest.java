package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteRequest {

    private Integer idUsuario;

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "El documento de identidad es obligatorio")
    private String documentoIdentidad;

    private LocalDate fechaNacimiento;

    private String sexo;

    private String telefono;

    @Email(message = "El correo no es válido")
    private String correo;

    private String direccion;

    private String alergias;

    private String observaciones;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}