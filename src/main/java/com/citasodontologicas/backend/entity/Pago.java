package com.citasodontologicas.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    // RELACIÓN CON CITA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", nullable = false)
    @JsonIgnoreProperties({"pagos"})
    private Cita cita;

    @Column(nullable = false)
    private Double monto;

    @Column(length = 50)
    private String metodo; // YAPE / PLIN / EFECTIVO / TARJETA

    @Column(length = 50)
    private String estado; // PENDIENTE / PAGADO / FALLIDO

    private LocalDateTime fechaPago;

    @Column(length = 100)
    private String transaccionId;
}