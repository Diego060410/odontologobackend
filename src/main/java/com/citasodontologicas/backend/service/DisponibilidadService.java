package com.citasodontologicas.backend.service;

import java.time.LocalDate;
import java.util.List;

public interface DisponibilidadService {

    List<Object> obtenerHorasDisponibles(
            Integer idOdontologo,
            LocalDate fecha
    );

}