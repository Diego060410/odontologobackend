package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {
    Optional<Especialidad> findByNombreEspecialidad(String nombreEspecialidad);
    boolean existsByNombreEspecialidad(String nombreEspecialidad);
}