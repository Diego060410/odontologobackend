package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.EstadoCitaRequest;
import com.citasodontologicas.backend.entity.EstadoCita;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.EstadoCitaRepository;
import com.citasodontologicas.backend.service.EstadoCitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoCitaServiceImpl implements EstadoCitaService {

    private final EstadoCitaRepository estadoCitaRepository;

    @Override
    public List<EstadoCita> listar() {
        return estadoCitaRepository.findAll();
    }

    @Override
    public EstadoCita obtenerPorId(Integer id) {
        return estadoCitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de cita no encontrado con id: " + id));
    }

    @Override
    public EstadoCita guardar(EstadoCitaRequest request) {
        if (estadoCitaRepository.existsByNombreEstado(request.getNombreEstado())) {
            throw new BadRequestException("Ya existe un estado de cita con ese nombre");
        }

        EstadoCita estadoCita = EstadoCita.builder()
                .nombreEstado(request.getNombreEstado())
                .descripcion(request.getDescripcion())
                .build();

        return estadoCitaRepository.save(estadoCita);
    }

    @Override
    public EstadoCita actualizar(Integer id, EstadoCitaRequest request) {
        EstadoCita estadoCita = obtenerPorId(id);

        if (!estadoCita.getNombreEstado().equalsIgnoreCase(request.getNombreEstado())
                && estadoCitaRepository.existsByNombreEstado(request.getNombreEstado())) {
            throw new BadRequestException("Ya existe un estado de cita con ese nombre");
        }

        estadoCita.setNombreEstado(request.getNombreEstado());
        estadoCita.setDescripcion(request.getDescripcion());

        return estadoCitaRepository.save(estadoCita);
    }

    @Override
    public void eliminar(Integer id) {
        EstadoCita estadoCita = obtenerPorId(id);
        estadoCitaRepository.delete(estadoCita);
    }
}
