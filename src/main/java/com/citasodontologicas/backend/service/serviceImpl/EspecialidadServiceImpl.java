package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.EspecialidadRequest;
import com.citasodontologicas.backend.entity.Especialidad;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.EspecialidadRepository;
import com.citasodontologicas.backend.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    @Override
    public List<Especialidad> listar() {
        return especialidadRepository.findAll();
    }

    @Override
    public Especialidad obtenerPorId(Integer id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id: " + id));
    }

    @Override
    public Especialidad guardar(EspecialidadRequest request) {
        if (especialidadRepository.existsByNombreEspecialidad(request.getNombreEspecialidad())) {
            throw new BadRequestException("Ya existe una especialidad con ese nombre");
        }

        Especialidad especialidad = Especialidad.builder()
                .nombreEspecialidad(request.getNombreEspecialidad())
                .descripcion(request.getDescripcion())
                .estado(request.getEstado())
                .build();

        return especialidadRepository.save(especialidad);
    }

    @Override
    public Especialidad actualizar(Integer id, EspecialidadRequest request) {
        Especialidad especialidad = obtenerPorId(id);

        if (!especialidad.getNombreEspecialidad().equalsIgnoreCase(request.getNombreEspecialidad())
                && especialidadRepository.existsByNombreEspecialidad(request.getNombreEspecialidad())) {
            throw new BadRequestException("Ya existe una especialidad con ese nombre");
        }

        especialidad.setNombreEspecialidad(request.getNombreEspecialidad());
        especialidad.setDescripcion(request.getDescripcion());
        especialidad.setEstado(request.getEstado());

        return especialidadRepository.save(especialidad);
    }

    @Override
    public void eliminar(Integer id) {
        Especialidad especialidad = obtenerPorId(id);
        especialidadRepository.delete(especialidad);
    }
}
