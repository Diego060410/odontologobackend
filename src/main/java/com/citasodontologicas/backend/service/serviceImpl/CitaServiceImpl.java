package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.CitaRequest;
import com.citasodontologicas.backend.entity.*;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.*;
import com.citasodontologicas.backend.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PagoRepository pagoRepository;

    @Override
    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    // Asegúrate de inyectar EstadoCitaRepository en el constructor si usas la opción A
    @Override
    @Transactional
    public Cita actualizarEstado(Integer id, Integer idEstado) {

        // 1. Buscar cita
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cita no encontrada"));

        // 2. Validación SOLO para cancelación
        if (idEstado == 3) {

            LocalDateTime fechaHoraCita =
                    LocalDateTime.of(cita.getFecha(), cita.getHoraInicio());

            LocalDateTime ahora = LocalDateTime.now();

            // 🔥 DIFERENCIA REAL EN HORAS
            long horasRestantes =
                    java.time.Duration.between(ahora, fechaHoraCita).toHours();

            System.out.println("HORAS RESTANTES: " + horasRestantes);

            // 🔥 VALIDACIÓN
            if (horasRestantes < 48) {

                throw new BadRequestException(
                        "No se puede cancelar la cita: Debe hacerse con al menos 48 horas de anticipación."
                );
            }
        }

        // 3. Buscar nuevo estado
        EstadoCita nuevoEstado = estadoCitaRepository.findById(idEstado)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El estado " + idEstado + " no existe"
                        ));

        // 4. Actualizar estado
        cita.setEstadoCita(nuevoEstado);

        // 5. Guardar
        return citaRepository.save(cita);
    }



    @Override // Asegúrate de agregar el @Override ya que está en la interfaz
    public List<Cita> listarPorOdontologo(Integer idOdontologo) {
        // CAMBIO AQUÍ: Debe coincidir con el nombre en CitaRepository
        return citaRepository.findByOdontologo_IdOdontologo(idOdontologo);
    }

    @Override
    public Cita obtenerPorId(Integer id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la cita con ID: " + id));
    }

    @Override
    @Transactional // 🔥 Importante para asegurar que se guarde la cita y el pago juntos
    public Cita guardar(CitaRequest request) {

        // 1. VALIDACIÓN BÁSICA DE HORAS
        if (request.getHoraInicio().isAfter(request.getHoraFin()) ||
                request.getHoraInicio().equals(request.getHoraFin())) {
            throw new BadRequestException("La hora de inicio debe ser menor que la hora de fin");
        }

        // 2. 🔥 VALIDAR CRUCE DE CITAS (CRÍTICO)
        List<Cita> conflictos = citaRepository.buscarConflictos(
                request.getIdOdontologo(),
                request.getFecha(),
                request.getHoraInicio(),
                request.getHoraFin()
        );

        if (!conflictos.isEmpty()) {
            throw new BadRequestException("El odontólogo ya tiene una cita programada en ese rango horario.");
        }

        // 3. BUSCAR ENTIDADES RELACIONADAS
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

        Usuario registradoPor = null;
        if (request.getRegistradoPor() != null) {
            registradoPor = usuarioRepository.findById(request.getRegistradoPor())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getRegistradoPor()));
        }

        // 4. ESTADO INICIAL (Usamos PENDIENTE_PAGO como tenías originalmente)
        EstadoCita estadoPendientePago = estadoCitaRepository
                .findByNombreEstado("PENDIENTE_PAGO")
                .orElseThrow(() -> new RuntimeException("Debe existir el estado PENDIENTE_PAGO en la base de datos"));

        // 5. CREAR OBJETO CITA
        Cita cita = Cita.builder()
                .paciente(paciente)
                .odontologo(odontologo)
                .sede(sede)
                .consultorio(consultorio)
                .estadoCita(estadoPendientePago)
                .fecha(request.getFecha())
                .horaInicio(request.getHoraInicio())
                .horaFin(request.getHoraFin())
                .motivo(request.getMotivo())
                .observaciones(request.getObservaciones())
                .fechaRegistro(LocalDateTime.now())
                .registradoPor(registradoPor)
                .build();

        Cita citaGuardada = citaRepository.save(cita);

        // 6. 🔥 CREAR REGISTRO DE PAGO AUTOMÁTICO
        // Esto permite que el sistema rastree la deuda desde que se crea la cita
        Pago pago = Pago.builder()
                .cita(citaGuardada)
                .monto(50.0) // Puedes parametrizar este monto según el servicio
                .metodo("PENDIENTE")
                .estado("PENDIENTE")
                .fechaPago(null)
                .transaccionId(null)
                .build();

        pagoRepository.save(pago);

        return citaGuardada;
    }

    @Override
    @Transactional
    public Cita actualizar(Integer id, CitaRequest request) {
        Cita cita = obtenerPorId(id);

        // Validar si el nuevo horario choca con otros (excluyendo la cita actual)
        // Podrías añadir lógica extra aquí para evitar auto-conflicto,
        // pero por ahora actualizamos campos básicos:
        cita.setFecha(request.getFecha());
        cita.setHoraInicio(request.getHoraInicio());
        cita.setHoraFin(request.getHoraFin());
        cita.setMotivo(request.getMotivo());
        cita.setObservaciones(request.getObservaciones());

        return citaRepository.save(cita);
    }


    @Override
    @Transactional
    public void eliminar(Integer id) {
        Cita cita = obtenerPorId(id);
        citaRepository.delete(cita);
    }
}