package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.HistorialCitaRequest;
import com.citasodontologicas.backend.entity.HistorialCita;

import java.util.List;

public interface HistorialCitaService {

    List<HistorialCita> listar();

    HistorialCita obtenerPorId(Integer id);

    HistorialCita guardar(HistorialCitaRequest request);

    HistorialCita actualizar(Integer id, HistorialCitaRequest request);

    void eliminar(Integer id);
}