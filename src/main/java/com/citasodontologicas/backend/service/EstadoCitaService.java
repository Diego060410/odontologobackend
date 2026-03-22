package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.EstadoCitaRequest;
import com.citasodontologicas.backend.entity.EstadoCita;

import java.util.List;

public interface EstadoCitaService {

    List<EstadoCita> listar();

    EstadoCita obtenerPorId(Integer id);

    EstadoCita guardar(EstadoCitaRequest request);

    EstadoCita actualizar(Integer id, EstadoCitaRequest request);

    void eliminar(Integer id);
}