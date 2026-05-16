package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.HorarioOdontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HorarioOdontologoRepository extends JpaRepository<HorarioOdontologo, Integer> {

    List<HorarioOdontologo> findByOdontologo_IdOdontologo(Integer idOdontologo);

    List<HorarioOdontologo> findByOdontologoIdOdontologo(Integer idOdontologo);

    // 🔥 BUSQUEDA POR FECHA: Este es el que hará que aparezcan las horas en el calendario
    List<HorarioOdontologo> findByOdontologo_IdOdontologoAndFechaAndEstadoTrue(Integer idOdontologo, LocalDate fecha);

    List<HorarioOdontologo> findBySede_IdSede(Integer idSede);
}