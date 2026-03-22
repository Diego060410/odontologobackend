package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.RolRequest;
import com.citasodontologicas.backend.entity.Rol;

import java.util.List;

public interface RolService {

    List<Rol> listar();

    Rol obtenerPorId(Integer id);

    Rol guardar(RolRequest request);

    Rol actualizar(Integer id, RolRequest request);

    void eliminar(Integer id);
}