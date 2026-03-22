package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.ConsultorioRequest;
import com.citasodontologicas.backend.entity.Consultorio;
import com.citasodontologicas.backend.entity.Sede;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.ConsultorioRepository;
import com.citasodontologicas.backend.repository.SedeRepository;
import com.citasodontologicas.backend.service.ConsultorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultorioServiceImpl implements ConsultorioService {

    private final ConsultorioRepository consultorioRepository;
    private final SedeRepository sedeRepository;

    @Override
    public List<Consultorio> listar() {
        return consultorioRepository.findAll();
    }

    @Override
    public Consultorio obtenerPorId(Integer id) {
        return consultorioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado con id: " + id));
    }

    @Override
    public Consultorio guardar(ConsultorioRequest request) {
        if (consultorioRepository.existsByNombreConsultorio(request.getNombreConsultorio())) {
            throw new BadRequestException("Ya existe un consultorio con ese nombre");
        }

        Sede sede = sedeRepository.findById(request.getIdSede())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + request.getIdSede()));

        Consultorio consultorio = Consultorio.builder()
                .sede(sede)
                .nombreConsultorio(request.getNombreConsultorio())
                .piso(request.getPiso())
                .descripcion(request.getDescripcion())
                .estado(request.getEstado())
                .build();

        return consultorioRepository.save(consultorio);
    }

    @Override
    public Consultorio actualizar(Integer id, ConsultorioRequest request) {
        Consultorio consultorio = obtenerPorId(id);

        if (!consultorio.getNombreConsultorio().equalsIgnoreCase(request.getNombreConsultorio())
                && consultorioRepository.existsByNombreConsultorio(request.getNombreConsultorio())) {
            throw new BadRequestException("Ya existe un consultorio con ese nombre");
        }

        Sede sede = sedeRepository.findById(request.getIdSede())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + request.getIdSede()));

        consultorio.setSede(sede);
        consultorio.setNombreConsultorio(request.getNombreConsultorio());
        consultorio.setPiso(request.getPiso());
        consultorio.setDescripcion(request.getDescripcion());
        consultorio.setEstado(request.getEstado());

        return consultorioRepository.save(consultorio);
    }

    @Override
    public void eliminar(Integer id) {
        Consultorio consultorio = obtenerPorId(id);
        consultorioRepository.delete(consultorio);
    }
}