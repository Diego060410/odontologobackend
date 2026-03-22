package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.HistorialCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistorialCitaRepository extends JpaRepository<HistorialCita, Integer> {
    List<HistorialCita> findByCita_IdCita(Integer idCita);
}