package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByPaciente_IdPaciente(Integer idPaciente);
    List<Cita> findByOdontologo_IdOdontologo(Integer idOdontologo);
    List<Cita> findBySede_IdSede(Integer idSede);
    List<Cita> findByEstadoCita_IdEstadoCita(Integer idEstadoCita);
    List<Cita> findByFecha(LocalDate fecha);
    List<Cita> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}