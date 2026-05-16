package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.HorarioOdontologoRequest;
import com.citasodontologicas.backend.entity.Consultorio; // ✅ Importado
import com.citasodontologicas.backend.entity.HorarioOdontologo;
import com.citasodontologicas.backend.entity.Odontologo;
import com.citasodontologicas.backend.entity.Sede;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.ConsultorioRepository; // ✅ Importado
import com.citasodontologicas.backend.repository.HorarioOdontologoRepository;
import com.citasodontologicas.backend.repository.OdontologoRepository;
import com.citasodontologicas.backend.repository.SedeRepository;
import com.citasodontologicas.backend.service.HorarioOdontologoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class HorarioOdontologoServiceImpl implements HorarioOdontologoService {

    private final HorarioOdontologoRepository horarioOdontologoRepository;
    private final OdontologoRepository odontologoRepository;
    private final SedeRepository sedeRepository;
    private final ConsultorioRepository consultorioRepository; // ✅ AGREGADO

    @Override
    public List<HorarioOdontologo> listar() {
        return horarioOdontologoRepository.findAll();
    }

    @Override
    public List<HorarioOdontologo> listarPorOdontologo(Integer idOdontologo) {
        return horarioOdontologoRepository.findByOdontologoIdOdontologo(idOdontologo);
    }

    @Override
    public HorarioOdontologo obtenerPorId(Integer id) {
        return horarioOdontologoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horario no encontrado con id: " + id));
    }

    @Override
    public HorarioOdontologo guardar(HorarioOdontologoRequest request) {
        validarRequest(request);

        Odontologo odontologo = odontologoRepository.findById(request.getIdOdontologo())
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado con id: " + request.getIdOdontologo()));

        Sede sede = sedeRepository.findById(request.getIdSede())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + request.getIdSede()));

        // ✅ BUSCAR CONSULTORIO (Esto evitara el error de columna null)
        Consultorio consultorio = consultorioRepository.findById(request.getIdConsultorio())
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado con id: " + request.getIdConsultorio()));

        String diaCalculado = request.getFecha().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                .toUpperCase();

        HorarioOdontologo horario = HorarioOdontologo.builder()
                .odontologo(odontologo)
                .sede(sede)
                .consultorio(consultorio) // ✅ ASIGNADO AQUÍ
                .fecha(request.getFecha())
                .diaSemana(diaCalculado)
                .horaInicio(request.getHoraInicio())
                .horaFin(request.getHoraFin())
                .estado(request.getEstado() != null ? request.getEstado() : true)
                .build();

        return horarioOdontologoRepository.save(horario);
    }

    @Override
    public HorarioOdontologo actualizar(Integer id, HorarioOdontologoRequest request) {
        HorarioOdontologo horario = obtenerPorId(id);
        validarRequest(request);

        Odontologo odontologo = odontologoRepository.findById(request.getIdOdontologo())
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado con id: " + request.getIdOdontologo()));

        Sede sede = sedeRepository.findById(request.getIdSede())
                .orElseThrow(() -> new ResourceNotFoundException("Sede no encontrada con id: " + request.getIdSede()));

        // ✅ BUSCAR CONSULTORIO PARA ACTUALIZAR
        Consultorio consultorio = consultorioRepository.findById(request.getIdConsultorio())
                .orElseThrow(() -> new ResourceNotFoundException("Consultorio no encontrado con id: " + request.getIdConsultorio()));

        String diaCalculado = request.getFecha().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                .toUpperCase();

        horario.setOdontologo(odontologo);
        horario.setSede(sede);
        horario.setConsultorio(consultorio); // ✅ ACTUALIZADO AQUÍ
        horario.setFecha(request.getFecha());
        horario.setDiaSemana(diaCalculado);
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

    private void validarRequest(HorarioOdontologoRequest request) {
        if (request.getFecha() == null) {
            throw new BadRequestException("La fecha es obligatoria");
        }
        if (request.getIdConsultorio() == null) { // ✅ Validación extra
            throw new BadRequestException("El id del consultorio es obligatorio");
        }
        if (request.getHoraInicio() == null || request.getHoraFin() == null) {
            throw new BadRequestException("Las horas son obligatorias");
        }
        if (request.getHoraInicio().isAfter(request.getHoraFin())) {
            throw new BadRequestException("La hora de inicio debe ser menor a la de fin");
        }
    }
}