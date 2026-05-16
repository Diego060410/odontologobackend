package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface OdontologoRepository extends JpaRepository<Odontologo, Integer> {
    Optional<Odontologo> findByUsuarioIdUsuario(Integer idUsuario);
    Optional<Odontologo> findByNumeroColegiatura(String numeroColegiatura);
    boolean existsByNumeroColegiatura(String numeroColegiatura);
    boolean existsByUsuario_IdUsuario(Integer idUsuario);
    List<Odontologo> findByEspecialidad_IdEspecialidad(Integer idEspecialidad);

    @Query("""
    SELECT DISTINCT o
    FROM Odontologo o
    JOIN o.horarios h
    WHERE h.consultorio.idConsultorio = :idConsultorio
    AND o.estado = true
    AND h.estado = true
    """)
    List<Odontologo> listarPorConsultorio(
            @Param("idConsultorio") Integer idConsultorio
    );
}