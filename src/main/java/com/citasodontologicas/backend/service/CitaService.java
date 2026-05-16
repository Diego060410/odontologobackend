package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.CitaRequest;
import com.citasodontologicas.backend.entity.Cita;

import java.util.List;

public interface CitaService {

    List<Cita> listar();

    List<Cita> listarPorOdontologo(Integer idOdontologo);

    Cita obtenerPorId(Integer id);

    Cita actualizarEstado(Integer id, Integer idEstado);

    Cita guardar(CitaRequest request);

    Cita actualizar(Integer id, CitaRequest request);

    void eliminar(Integer id);
}