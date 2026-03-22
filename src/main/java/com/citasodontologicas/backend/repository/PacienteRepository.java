package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByDocumentoIdentidad(String documentoIdentidad);
    boolean existsByDocumentoIdentidad(String documentoIdentidad);
    boolean existsByUsuario_IdUsuario(Integer idUsuario);
}