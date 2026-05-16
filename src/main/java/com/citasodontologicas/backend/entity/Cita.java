package com.citasodontologicas.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "cita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Integer idCita;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    @JsonIgnoreProperties({"citas"})
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_odontologo", nullable = false)
    @JsonIgnoreProperties({"horarios", "citas"})
    private Odontologo odontologo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sede", nullable = false)
    @JsonIgnoreProperties({"consultorios", "horarios", "citas"})
    private Sede sede;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_consultorio")
    @JsonIgnoreProperties({"citas"})
    private Consultorio consultorio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_estado_cita", nullable = false)
    @JsonIgnoreProperties({"citas"})
    private EstadoCita estadoCita;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "motivo", length = 250)
    private String motivo;

    @Column(name = "observaciones", length = 300)
    private String observaciones;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "registrado_por")
    private Usuario registradoPor;

    // HISTORIAL
    @OneToMany(mappedBy = "cita", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HistorialCita> historial;

    // NOTIFICACIONES
    @OneToMany(mappedBy = "cita", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Notificacion> notificaciones;

    // 🔥 RELACIÓN CLAVE: PAGOS
    @OneToMany(mappedBy = "cita", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pago> pagos;
}