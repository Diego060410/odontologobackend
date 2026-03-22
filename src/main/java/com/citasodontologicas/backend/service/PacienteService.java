package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.PacienteRequest;
import com.citasodontologicas.backend.entity.Paciente;

import java.util.List;

public interface PacienteService {

    List<Paciente> listar();

    Paciente obtenerPorId(Integer id);

    Paciente guardar(PacienteRequest request);

    Paciente actualizar(Integer id, PacienteRequest request);

    void eliminar(Integer id);
}