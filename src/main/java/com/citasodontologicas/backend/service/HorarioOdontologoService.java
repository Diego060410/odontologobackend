package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.HorarioOdontologoRequest;
import com.citasodontologicas.backend.entity.HorarioOdontologo;

import java.util.List;

public interface HorarioOdontologoService {

    List<HorarioOdontologo> listar();

    HorarioOdontologo obtenerPorId(Integer id);

    HorarioOdontologo guardar(HorarioOdontologoRequest request);

    HorarioOdontologo actualizar(Integer id, HorarioOdontologoRequest request);

    void eliminar(Integer id);
}