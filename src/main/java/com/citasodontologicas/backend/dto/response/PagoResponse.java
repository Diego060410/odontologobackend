package com.citasodontologicas.backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PagoResponse {

    private Integer idPago;
    private Integer idCita;
    private Double monto;
    private String metodo;
    private String estado;
    private LocalDateTime fechaPago;
    private String transaccionId;
}