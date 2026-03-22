package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.RolRequest;
import com.citasodontologicas.backend.entity.Rol;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.RolRepository;
import com.citasodontologicas.backend.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    @Override
    public Rol obtenerPorId(Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id: " + id));
    }

    @Override
    public Rol guardar(RolRequest request) {
        if (rolRepository.existsByNombreRol(request.getNombreRol())) {
            throw new BadRequestException("Ya existe un rol con ese nombre");
        }

        Rol rol = Rol.builder()
                .nombreRol(request.getNombreRol())
                .descripcion(request.getDescripcion())
                .estado(request.getEstado())
                .build();

        return rolRepository.save(rol);
    }

    @Override
    public Rol actualizar(Integer id, RolRequest request) {
        Rol rol = obtenerPorId(id);

        if (!rol.getNombreRol().equalsIgnoreCase(request.getNombreRol())
                && rolRepository.existsByNombreRol(request.getNombreRol())) {
            throw new BadRequestException("Ya existe un rol con ese nombre");
        }

        rol.setNombreRol(request.getNombreRol());
        rol.setDescripcion(request.getDescripcion());
        rol.setEstado(request.getEstado());

        return rolRepository.save(rol);
    }

    @Override
    public void eliminar(Integer id) {
        Rol rol = obtenerPorId(id);
        rolRepository.delete(rol);
    }
}