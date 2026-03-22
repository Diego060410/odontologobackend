package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoCitaRepository extends JpaRepository<EstadoCita, Integer> {
    Optional<EstadoCita> findByNombreEstado(String nombreEstado);
    boolean existsByNombreEstado(String nombreEstado);
}