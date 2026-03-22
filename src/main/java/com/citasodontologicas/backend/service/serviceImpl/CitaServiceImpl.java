package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.CitaRequest;
import com.citasodontologicas.backend.entity.*;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.*;
import com.citasodontologicas.backend.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final OdontologoRepository odontologoRepository;
    private final SedeRepository sedeRepository;
    private final ConsultorioRepository consultorioRepository;
    private final EstadoCitaRepository estadoCitaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    @Override
    public Cita obtenerPorId(Integer id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
    }

    @Override
    public Cita guardar(CitaRequest request) {
        if (request.getHoraInicio().isAfter(request.getHoraFin()) || request.getHoraInicio().equals(request.getHoraFin())) {
            throw new BadRequestException("La hora de inicio debe ser menor que la hora de fin");
        }

        Paciente paciente = pacienteRepository.findById(request.getIdPaciente())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + request.getIdPaciente()));

        Odontologo odontologo = odontologoRepository.findById(request.getIdOdontologo())
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado con id: " + request.getIdOdontologo()));

        Sede sede = sedeRepository.findById(request.getIdSede())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + request.getIdSede()));

        Consultorio consultorio = null;
        if (request.getIdConsultorio() != null) {
            consultorio = consultorioRepository.findById(request.getIdConsultorio())
                    .orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado con id: " + request.getIdConsultorio()));
        }

        EstadoCita estadoCita = estadoCitaRepository.findById(request.getIdEstadoCita())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de cita no encontrado con id: " + request.getIdEstadoCita()));

        Usuario registradoPor = null;
        if (request.getRegistradoPor() != null) {
            registradoPor = usuarioRepository.findById(request.getRegistradoPor())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getRegistradoPor()));
        }

        Cita cita = Cita.builder()
                .paciente(paciente)
                .odontologo(odontologo)
                .sede(sede)
                .consultorio(consultorio)
                .estadoCita(estadoCita)
                .fecha(request.getFecha())
                .horaInicio(request.getHoraInicio())
                .horaFin(request.getHoraFin())
                .motivo(request.getMotivo())
                .observaciones(request.getObservaciones())
                .fechaRegistro(LocalDateTime.now())
                .registradoPor(registradoPor)
                .build();

        return citaRepository.save(cita);
    }

    @Override
    public Cita actualizar(Integer id, CitaRequest request) {
        Cita cita = obtenerPorId(id);

        if (request.getHoraInicio().isAfter(request.getHoraFin()) || request.getHoraInicio().equals(request.getHoraFin())) {
            throw new BadRequestException("La hora de inicio debe ser menor que la hora de fin");
        }

        Paciente paciente = pacienteRepository.findById(request.getIdPaciente())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + request.getIdPaciente()));

        Odontologo odontologo = odontologoRepository.findById(request.getIdOdontologo())
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado con id: " + request.getIdOdontologo()));

        Sede sede = sedeRepository.findById(request.getIdSede())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + request.getIdSede()));

        Consultorio consultorio = null;
        if (request.getIdConsultorio() != null) {
            consultorio = consultorioRepository.findById(request.getIdConsultorio())
                    .orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado con id: " + request.getIdConsultorio()));
        }

        EstadoCita estadoCita = estadoCitaRepository.findById(request.getIdEstadoCita())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de cita no encontrado con id: " + request.getIdEstadoCita()));

        Usuario registradoPor = null;
        if (request.getRegistradoPor() != null) {
            registradoPor = usuarioRepository.findById(request.getRegistradoPor())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getRegistradoPor()));
        }

        cita.setPaciente(paciente);
        cita.setOdontologo(odontologo);
        cita.setSede(sede);
        cita.setConsultorio(consultorio);
        cita.setEstadoCita(estadoCita);
        cita.setFecha(request.getFecha());
        cita.setHoraInicio(request.getHoraInicio());
        cita.setHoraFin(request.getHoraFin());
        cita.setMotivo(request.getMotivo());
        cita.setObservaciones(request.getObservaciones());
        cita.setRegistradoPor(registradoPor);

        return citaRepository.save(cita);
    }

    @Override
    public void eliminar(Integer id) {
        Cita cita = obtenerPorId(id);
        citaRepository.delete(cita);
    }
}