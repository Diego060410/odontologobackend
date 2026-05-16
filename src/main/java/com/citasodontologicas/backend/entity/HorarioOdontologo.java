package com.citasodontologicas.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "horario_odontologo", uniqueConstraints = {
        // Esto evita que el mismo odontólogo tenga dos registros a la misma hora el mismo día
        @UniqueConstraint(columnNames = {"id_odontologo", "fecha", "hora_inicio"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioOdontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario")
    private Integer idHorario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_odontologo", nullable = false)
    @JsonIgnoreProperties({"horarios", "citas"})
    private Odontologo odontologo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_consultorio", nullable = false)
    @JsonIgnoreProperties({"citas", "horarios", "odontologo", "sede"})
    private Consultorio consultorio;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha; // Ya está perfecto

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sede", nullable = false)
    @JsonIgnoreProperties({"consultorios", "horarios", "citas"})
    private Sede sede;

    @Column(name = "dia_semana", length = 20)
    private String diaSemana; // Ahora es opcional, ya que manda la fecha

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;
}