package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.NotificacionRequest;
import com.citasodontologicas.backend.entity.Notificacion;

import java.util.List;

public interface NotificacionService {

    List<Notificacion> listar();

    Notificacion obtenerPorId(Integer id);

    Notificacion guardar(NotificacionRequest request);

    Notificacion actualizar(Integer id, NotificacionRequest request);

    void eliminar(Integer id);
}