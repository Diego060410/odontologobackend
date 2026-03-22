package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.EspecialidadRequest;
import com.citasodontologicas.backend.entity.Especialidad;

import java.util.List;

public interface EspecialidadService {

    List<Especialidad> listar();

    Especialidad obtenerPorId(Integer id);

    Especialidad guardar(EspecialidadRequest request);

    Especialidad actualizar(Integer id, EspecialidadRequest request);

    void eliminar(Integer id);
}