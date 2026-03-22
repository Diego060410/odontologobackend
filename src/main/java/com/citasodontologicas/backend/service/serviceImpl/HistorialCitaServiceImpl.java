package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.HistorialCitaRequest;
import com.citasodontologicas.backend.entity.Cita;
import com.citasodontologicas.backend.entity.HistorialCita;
import com.citasodontologicas.backend.entity.Usuario;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.CitaRepository;
import com.citasodontologicas.backend.repository.HistorialCitaRepository;
import com.citasodontologicas.backend.repository.UsuarioRepository;
import com.citasodontologicas.backend.service.HistorialCitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistorialCitaServiceImpl implements HistorialCitaService {

    private final HistorialCitaRepository historialCitaRepository;
    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<HistorialCita> listar() {
        return historialCitaRepository.findAll();
    }

    @Override
    public HistorialCita obtenerPorId(Integer id) {
        return historialCitaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historial de cita no encontrado con id: " + id));
    }

    @Override
    public HistorialCita guardar(HistorialCitaRequest request) {
        Cita cita = citaRepository.findById(request.getIdCita())
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + request.getIdCita()));

        Usuario usuario = null;
        if (request.getRealizadoPor() != null) {
            usuario = usuarioRepository.findById(request.getRealizadoPor())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getRealizadoPor()));
        }

        HistorialCita historial = HistorialCita.builder()
                .cita(cita)
                .accion(request.getAccion())
                .descripcion(request.getDescripcion())
                .fechaAccion(LocalDateTime.now())
                .realizadoPor(usuario)
                .build();

        return historialCitaRepository.save(historial);
    }

    @Override
    public HistorialCita actualizar(Integer id, HistorialCitaRequest request) {
        HistorialCita historial = obtenerPorId(id);

        Cita cita = citaRepository.findById(request.getIdCita())
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + request.getIdCita()));

        Usuario usuario = null;
        if (request.getRealizadoPor() != null) {
            usuario = usuarioRepository.findById(request.getRealizadoPor())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getRealizadoPor()));
        }

        historial.setCita(cita);
        historial.setAccion(request.getAccion());
        historial.setDescripcion(request.getDescripcion());
        historial.setRealizadoPor(usuario);

        return historialCitaRepository.save(historial);
    }

    @Override
    public void eliminar(Integer id) {
        HistorialCita historial = obtenerPorId(id);
        historialCitaRepository.delete(historial);
    }
}
