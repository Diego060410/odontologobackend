package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.NotificacionRequest;
import com.citasodontologicas.backend.entity.Cita;
import com.citasodontologicas.backend.entity.Notificacion;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.CitaRepository;
import com.citasodontologicas.backend.repository.NotificacionRepository;
import com.citasodontologicas.backend.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final CitaRepository citaRepository;

    @Override
    public List<Notificacion> listar() {
        return notificacionRepository.findAll();
    }

    @Override
    public Notificacion obtenerPorId(Integer id) {
        return notificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificación no encontrada con id: " + id));
    }

    @Override
    public Notificacion guardar(NotificacionRequest request) {
        Cita cita = citaRepository.findById(request.getIdCita())
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + request.getIdCita()));

        Notificacion notificacion = Notificacion.builder()
                .cita(cita)
                .medio(request.getMedio())
                .destinatario(request.getDestinatario())
                .mensaje(request.getMensaje())
                .fechaEnvio(LocalDateTime.now())
                .estado(request.getEstado() != null ? request.getEstado() : "Pendiente")
                .build();

        return notificacionRepository.save(notificacion);
    }

    @Override
    public Notificacion actualizar(Integer id, NotificacionRequest request) {
        Notificacion notificacion = obtenerPorId(id);

        Cita cita = citaRepository.findById(request.getIdCita())
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + request.getIdCita()));

        notificacion.setCita(cita);
        notificacion.setMedio(request.getMedio());
        notificacion.setDestinatario(request.getDestinatario());
        notificacion.setMensaje(request.getMensaje());
        notificacion.setEstado(request.getEstado() != null ? request.getEstado() : notificacion.getEstado());

        return notificacionRepository.save(notificacion);
    }

    @Override
    public void eliminar(Integer id) {
        Notificacion notificacion = obtenerPorId(id);
        notificacionRepository.delete(notificacion);
    }
}