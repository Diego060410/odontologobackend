package com.citasodontologicas.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "consultorio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultorio")
    private Integer idConsultorio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sede", nullable = false)
    @JsonIgnoreProperties({"consultorios", "horarios", "citas"})
    private Sede sede;

    @Column(name = "nombre_consultorio", nullable = false, length = 100)
    private String nombreConsultorio;

    @Column(name = "piso", length = 20)
    private String piso;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @OneToMany(mappedBy = "consultorio", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cita> citas;
}