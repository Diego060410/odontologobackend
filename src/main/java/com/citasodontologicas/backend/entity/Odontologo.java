package com.citasodontologicas.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "odontologo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_odontologo")
    private Integer idOdontologo;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    @JsonIgnoreProperties({"rol"})
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_especialidad", nullable = false)
    @JsonIgnoreProperties({"odontologos"})
    private Especialidad especialidad;

    @Column(name = "numero_colegiatura", nullable = false, unique = true, length = 50)
    private String numeroColegiatura;

    @Column(name = "estado", nullable = false)
    private Boolean estado = true;

    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY)
    private List<HorarioOdontologo> horarios;

    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY)
    private List<Cita> citas;
}