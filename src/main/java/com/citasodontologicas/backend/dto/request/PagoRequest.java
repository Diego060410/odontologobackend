package com.citasodontologicas.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequest {

    @NotNull
    private Integer idCita;

    @NotNull
    private Double monto;

    private String metodo;

    // ✅ Agregamos este campo para recibir el ID de PayPal
    private String transaccionId;
}