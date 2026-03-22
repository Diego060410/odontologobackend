package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SedeRepository extends JpaRepository<Sede, Integer> {
    boolean existsByNombreSede(String nombreSede);
}