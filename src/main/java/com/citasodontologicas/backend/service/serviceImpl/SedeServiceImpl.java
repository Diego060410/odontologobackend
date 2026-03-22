package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.SedeRequest;
import com.citasodontologicas.backend.entity.Sede;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.SedeRepository;
import com.citasodontologicas.backend.service.SedeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SedeServiceImpl implements SedeService {

    private final SedeRepository sedeRepository;

    @Override
    public List<Sede> listar() {
        return sedeRepository.findAll();
    }

    @Override
    public Sede obtenerPorId(Integer id) {
        return sedeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + id));
    }

    @Override
    public Sede guardar(SedeRequest request) {
        if (sedeRepository.existsByNombreSede(request.getNombreSede())) {
            throw new BadRequestException("Ya existe una sede con ese nombre");
        }

        Sede sede = Sede.builder()
                .nombreSede(request.getNombreSede())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .horaApertura(request.getHoraApertura())
                .horaCierre(request.getHoraCierre())
                .estado(request.getEstado())
                .build();

        return sedeRepository.save(sede);
    }

    @Override
    public Sede actualizar(Integer id, SedeRequest request) {
        Sede sede = obtenerPorId(id);

        if (!sede.getNombreSede().equalsIgnoreCase(request.getNombreSede())
                && sedeRepository.existsByNombreSede(request.getNombreSede())) {
            throw new BadRequestException("Ya existe una sede con ese nombre");
        }

        sede.setNombreSede(request.getNombreSede());
        sede.setDireccion(request.getDireccion());
        sede.setTelefono(request.getTelefono());
        sede.setCorreo(request.getCorreo());
        sede.setHoraApertura(request.getHoraApertura());
        sede.setHoraCierre(request.getHoraCierre());
        sede.setEstado(request.getEstado());

        return sedeRepository.save(sede);
    }

    @Override
    public void eliminar(Integer id) {
        Sede sede = obtenerPorId(id);
        sedeRepository.delete(sede);
    }
}
