package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByPaciente_IdPaciente(Integer idPaciente);

    List<Cita> findByOdontologoIdOdontologo(Integer id);

    List<Cita> findByOdontologo_IdOdontologo(Integer idOdontologo);

    List<Cita> findBySede_IdSede(Integer idSede);

    List<Cita> findByEstadoCita_IdEstadoCita(Integer idEstadoCita);

    List<Cita> findByFecha(LocalDate fecha);

    List<Cita> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    List<Cita> findByOdontologo_IdOdontologoAndFecha(Integer idOdontologo, LocalDate fecha);

    List<Cita> findByOdontologo_IdOdontologoAndFechaAndEstadoCita_IdEstadoCitaNot(
            Integer idOdontologo,
            LocalDate fecha,
            Integer idEstadoCita
    );

    // 🔥 VALIDAR CHOQUE DE CITAS (CRÍTICO)
    // Verifica si el odontólogo ya tiene una cita que se traslape con el rango horario solicitado
    @Query("""
        SELECT c FROM Cita c
        WHERE c.odontologo.idOdontologo = :idOdontologo
        AND c.fecha = :fecha
        AND c.estadoCita.idEstadoCita <> 3
        AND (
            (:horaInicio < c.horaFin AND :horaFin > c.horaInicio)
        )
    """)
    List<Cita> buscarConflictos(
            @Param("idOdontologo") Integer idOdontologo,
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );



}