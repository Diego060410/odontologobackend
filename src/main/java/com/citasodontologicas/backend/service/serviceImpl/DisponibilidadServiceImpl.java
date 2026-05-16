package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.entity.Cita;
import com.citasodontologicas.backend.entity.HorarioOdontologo;
import com.citasodontologicas.backend.repository.CitaRepository;
import com.citasodontologicas.backend.repository.HorarioOdontologoRepository;
import com.citasodontologicas.backend.service.DisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisponibilidadServiceImpl implements DisponibilidadService {

    private final HorarioOdontologoRepository horarioRepository;
    private final CitaRepository citaRepository;

    @Override
    public List<Object> obtenerHorasDisponibles(Integer idOdontologo, LocalDate fecha) {

        List<HorarioOdontologo> horarios = horarioRepository
                .findByOdontologo_IdOdontologoAndFechaAndEstadoTrue(idOdontologo, fecha);

        List<Cita> citas = citaRepository
                .findByOdontologo_IdOdontologoAndFechaAndEstadoCita_IdEstadoCitaNot(
                        idOdontologo,
                        fecha,
                        3
                );

        List<LocalTime> horasOcupadas = citas.stream()
                .map(Cita::getHoraInicio)
                .collect(Collectors.toList());

        List<Object> horas = new ArrayList<>();

        for (HorarioOdontologo h : horarios) {

            LocalTime inicio = h.getHoraInicio();
            LocalTime fin = h.getHoraFin();

            while (inicio.isBefore(fin)) {

                boolean ocupada = horasOcupadas.contains(inicio);

                Map<String, Object> horaData = new HashMap<>();
                horaData.put("hora", inicio.toString());
                horaData.put("ocupada", ocupada);

                horas.add(horaData);

                inicio = inicio.plusMinutes(30);
            }
        }

        return horas;
    }
}