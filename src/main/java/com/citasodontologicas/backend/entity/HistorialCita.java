package com.citasodontologicas.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_cita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cita", nullable = false)
    @JsonIgnoreProperties({"historial", "notificaciones"})
    private Cita cita;

    @Column(name = "accion", nullable = false, length = 50)
    private String accion;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "fecha_accion", nullable = false)
    private LocalDateTime fechaAccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "realizado_por")
    private Usuario realizadoPor;
}