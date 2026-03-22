package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByCita_IdCita(Integer idCita);
    List<Notificacion> findByEstado(String estado);
    List<Notificacion> findByMedio(String medio);
}