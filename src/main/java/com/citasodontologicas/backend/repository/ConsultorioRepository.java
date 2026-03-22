package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultorioRepository extends JpaRepository<Consultorio, Integer> {
    List<Consultorio> findBySede_IdSede(Integer idSede);
    boolean existsByNombreConsultorio(String nombreConsultorio);
}