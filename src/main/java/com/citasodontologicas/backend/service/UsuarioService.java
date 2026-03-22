package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.UsuarioRequest;
import com.citasodontologicas.backend.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario obtenerPorId(Integer id);

    Usuario guardar(UsuarioRequest request);

    Usuario actualizar(Integer id, UsuarioRequest request);

    void eliminar(Integer id);
}