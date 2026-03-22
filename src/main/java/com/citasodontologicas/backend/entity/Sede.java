package com.citasodontologicas.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "sede")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede")
    private Integer idSede;

    @Column(name = "nombre_sede", nullable = false, length = 100)
    private String nombreSede;

    @Column(name = "direccion", nullable = false, length = 200)
    private String direccion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "correo", length = 150)
    private String correo;

    @Column(name = "hora_apertura", nullable = false)
    private LocalTime horaApertura;

    @Column(name = "hora_cierre", nullable = false)
    private LocalTime horaCierre;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @OneToMany(mappedBy = "sede", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Consultorio> consultorios;

    @OneToMany(mappedBy = "sede", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HorarioOdontologo> horarios;

    @OneToMany(mappedBy = "sede", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cita> citas;
}