package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.SedeRequest;
import com.citasodontologicas.backend.entity.Sede;

import java.util.List;

public interface SedeService {

    List<Sede> listar();

    Sede obtenerPorId(Integer id);

    Sede guardar(SedeRequest request);

    Sede actualizar(Integer id, SedeRequest request);

    void eliminar(Integer id);
}