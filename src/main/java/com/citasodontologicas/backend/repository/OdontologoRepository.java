package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface OdontologoRepository extends JpaRepository<Odontologo, Integer> {
    Optional<Odontologo> findByNumeroColegiatura(String numeroColegiatura);
    boolean existsByNumeroColegiatura(String numeroColegiatura);
    boolean existsByUsuario_IdUsuario(Integer idUsuario);
    List<Odontologo> findByEspecialidad_IdEspecialidad(Integer idEspecialidad);
}