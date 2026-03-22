package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.OdontologoRequest;
import com.citasodontologicas.backend.entity.Odontologo;

import java.util.List;

public interface OdontologoService {

    List<Odontologo> listar();

    Odontologo obtenerPorId(Integer id);

    Odontologo guardar(OdontologoRequest request);

    Odontologo actualizar(Integer id, OdontologoRequest request);

    void eliminar(Integer id);
}