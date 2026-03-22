package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.ConsultorioRequest;
import com.citasodontologicas.backend.entity.Consultorio;

import java.util.List;

public interface ConsultorioService {

    List<Consultorio> listar();

    Consultorio obtenerPorId(Integer id);

    Consultorio guardar(ConsultorioRequest request);

    Consultorio actualizar(Integer id, ConsultorioRequest request);

    void eliminar(Integer id);
}