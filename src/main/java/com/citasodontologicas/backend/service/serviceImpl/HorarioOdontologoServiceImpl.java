package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.HorarioOdontologoRequest;
import com.citasodontologicas.backend.entity.HorarioOdontologo;
import com.citasodontologicas.backend.entity.Odontologo;
import com.citasodontologicas.backend.entity.Sede;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.HorarioOdontologoRepository;
import com.citasodontologicas.backend.repository.OdontologoRepository;
import com.citasodontologicas.backend.repository.SedeRepository;
import com.citasodontologicas.backend.service.HorarioOdontologoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioOdontologoServiceImpl implements HorarioOdontologoService {

    private final HorarioOdontologoRepository horarioOdontologoRepository;
    private final OdontologoRepository odontologoRepository;
    private final SedeRepository sedeRepository;

    @Override
    public List<HorarioOdontologo> listar() {
        return horarioOdontologoRepository.findAll();
    }

    @Override
    public HorarioOdontologo obtenerPorId(Integer id) {
        return horarioOdontologoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con id: " + id));
    }

    @Override
    public HorarioOdontologo guardar(HorarioOdontologoRequest request) {
        if (request.getHoraInicio().isAfter(request.getHoraFin()) || request.getHoraInicio().equals(request.getHoraFin())) {
            throw new BadRequestException("La hora de inicio debe ser menor que la hora de fin");
        }

        Odontologo odontologo = odontologoRepository.findById(request.getIdOdontologo())
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado con id: " + request.getIdOdontologo()));

        Sede sede = sedeRepository.findById(request.getIdSede())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + request.getIdSede()));

        HorarioOdontologo horario = HorarioOdontologo.builder()
                .odontologo(odontologo)
                .sede(sede)
                .diaSemana(request.getDiaSemana())
                .horaInicio(request.getHoraInicio())
                .horaFin(request.getHoraFin())
                .estado(request.getEstado())
                .build();

        return horarioOdontologoRepository.save(horario);
    }

    @Override
    public HorarioOdontologo actualizar(Integer id, HorarioOdontologoRequest request) {
        HorarioOdontologo horario = obtenerPorId(id);

        if (request.getHoraInicio().isAfter(request.getHoraFin()) || request.getHoraInicio().equals(request.getHoraFin())) {
            throw new BadRequestException("La hora de inicio debe ser menor que la hora de fin");
        }

        Odontologo odontologo = odontologoRepository.findById(request.getIdOdontologo())
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado con id: " + request.getIdOdontologo()));

        Sede sede = sedeRepository.findById(request.getIdSede())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + request.getIdSede()));

        horario.setOdontologo(odontologo);
        horario.setSede(sede);
        horario.setDiaSemana(request.getDiaSemana());
        horario.setHoraInicio(request.getHoraInicio());
        horario.setHoraFin(request.getHoraFin());
        horario.setEstado(request.getEstado());

        return horarioOdontologoRepository.save(horario);
    }

    @Override
    public void eliminar(Integer id) {
        HorarioOdontologo horario = obtenerPorId(id);
        horarioOdontologoRepository.delete(horario);
    }
}