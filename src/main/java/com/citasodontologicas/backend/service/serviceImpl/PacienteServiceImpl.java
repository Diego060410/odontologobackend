package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.PacienteRequest;
import com.citasodontologicas.backend.entity.Paciente;
import com.citasodontologicas.backend.entity.Usuario;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.PacienteRepository;
import com.citasodontologicas.backend.repository.UsuarioRepository;
import com.citasodontologicas.backend.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente obtenerPorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));
    }

    @Override
    public Paciente guardar(PacienteRequest request) {
        if (pacienteRepository.existsByDocumentoIdentidad(request.getDocumentoIdentidad())) {
            throw new BadRequestException("Ya existe un paciente con ese documento de identidad");
        }

        if (request.getSexo() != null &&
                !request.getSexo().equalsIgnoreCase("M") &&
                !request.getSexo().equalsIgnoreCase("F")) {
            throw new BadRequestException("El sexo solo puede ser M o F");
        }

        Usuario usuario = null;
        if (request.getIdUsuario() != null) {
            if (pacienteRepository.existsByUsuario_IdUsuario(request.getIdUsuario())) {
                throw new BadRequestException("Ese usuario ya está asignado a un paciente");
            }

            usuario = usuarioRepository.findById(request.getIdUsuario())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getIdUsuario()));
        }

        Paciente paciente = Paciente.builder()
                .usuario(usuario)
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .documentoIdentidad(request.getDocumentoIdentidad())
                .fechaNacimiento(request.getFechaNacimiento())
                .sexo(request.getSexo() != null ? request.getSexo().toUpperCase() : null)
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .direccion(request.getDireccion())
                .alergias(request.getAlergias())
                .observaciones(request.getObservaciones())
                .estado(request.getEstado())
                .fechaRegistro(LocalDateTime.now())
                .build();

        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente actualizar(Integer id, PacienteRequest request) {
        Paciente paciente = obtenerPorId(id);

        if (!paciente.getDocumentoIdentidad().equalsIgnoreCase(request.getDocumentoIdentidad())
                && pacienteRepository.existsByDocumentoIdentidad(request.getDocumentoIdentidad())) {
            throw new BadRequestException("Ya existe un paciente con ese documento de identidad");
        }

        if (request.getSexo() != null &&
                !request.getSexo().equalsIgnoreCase("M") &&
                !request.getSexo().equalsIgnoreCase("F")) {
            throw new BadRequestException("El sexo solo puede ser M o F");
        }

        Usuario usuario = null;
        if (request.getIdUsuario() != null) {
            if ((paciente.getUsuario() == null || !paciente.getUsuario().getIdUsuario().equals(request.getIdUsuario()))
                    && pacienteRepository.existsByUsuario_IdUsuario(request.getIdUsuario())) {
                throw new BadRequestException("Ese usuario ya está asignado a un paciente");
            }

            usuario = usuarioRepository.findById(request.getIdUsuario())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getIdUsuario()));
        }

        paciente.setUsuario(usuario);
        paciente.setNombres(request.getNombres());
        paciente.setApellidos(request.getApellidos());
        paciente.setDocumentoIdentidad(request.getDocumentoIdentidad());
        paciente.setFechaNacimiento(request.getFechaNacimiento());
        paciente.setSexo(request.getSexo() != null ? request.getSexo().toUpperCase() : null);
        paciente.setTelefono(request.getTelefono());
        paciente.setCorreo(request.getCorreo());
        paciente.setDireccion(request.getDireccion());
        paciente.setAlergias(request.getAlergias());
        paciente.setObservaciones(request.getObservaciones());
        paciente.setEstado(request.getEstado());

        return pacienteRepository.save(paciente);
    }

    @Override
    public void eliminar(Integer id) {
        Paciente paciente = obtenerPorId(id);
        pacienteRepository.delete(paciente);
    }
}
