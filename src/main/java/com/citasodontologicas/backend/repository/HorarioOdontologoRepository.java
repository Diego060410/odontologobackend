package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.HorarioOdontologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioOdontologoRepository extends JpaRepository<HorarioOdontologo, Integer> {
    List<HorarioOdontologo> findByOdontologo_IdOdontologo(Integer idOdontologo);
    List<HorarioOdontologo> findBySede_IdSede(Integer idSede);
}