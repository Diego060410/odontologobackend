package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class HistorialCitaRequest {

    @NotNull(message = "El id de la cita es obligatorio")
    private Integer idCita;

    @NotBlank(message = "La acción es obligatoria")
    private String accion;

    private String descripcion;

    private Integer realizadoPor;

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getRealizadoPor() {
        return realizadoPor;
    }

    public void setRealizadoPor(Integer realizadoPor) {
        this.realizadoPor = realizadoPor;
    }
}