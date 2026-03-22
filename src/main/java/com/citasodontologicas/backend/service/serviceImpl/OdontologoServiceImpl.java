package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.OdontologoRequest;
import com.citasodontologicas.backend.entity.Especialidad;
import com.citasodontologicas.backend.entity.Odontologo;
import com.citasodontologicas.backend.entity.Usuario;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.EspecialidadRepository;
import com.citasodontologicas.backend.repository.OdontologoRepository;
import com.citasodontologicas.backend.repository.UsuarioRepository;
import com.citasodontologicas.backend.service.OdontologoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OdontologoServiceImpl implements OdontologoService {

    private final OdontologoRepository odontologoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    public List<Odontologo> listar() {
        return odontologoRepository.findAll();
    }

    @Override
    public Odontologo obtenerPorId(Integer id) {
        return odontologoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado con id: " + id));
    }

    @Override
    public Odontologo guardar(OdontologoRequest request) {
        if (odontologoRepository.existsByNumeroColegiatura(request.getNumeroColegiatura())) {
            throw new BadRequestException("Ya existe un odontólogo con ese número de colegiatura");
        }

        if (odontologoRepository.existsByUsuario_IdUsuario(request.getIdUsuario())) {
            throw new BadRequestException("Ese usuario ya está asignado a un odontólogo");
        }

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getIdUsuario()));

        Especialidad especialidad = especialidadRepository.findById(request.getIdEspecialidad())
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id: " + request.getIdEspecialidad()));

        Odontologo odontologo = Odontologo.builder()
                .usuario(usuario)
                .especialidad(especialidad)
                .numeroColegiatura(request.getNumeroColegiatura())
                .estado(request.getEstado())
                .build();

        return odontologoRepository.save(odontologo);
    }

    @Override
    public Odontologo actualizar(Integer id, OdontologoRequest request) {
        Odontologo odontologo = obtenerPorId(id);

        if (!odontologo.getNumeroColegiatura().equalsIgnoreCase(request.getNumeroColegiatura())
                && odontologoRepository.existsByNumeroColegiatura(request.getNumeroColegiatura())) {
            throw new BadRequestException("Ya existe un odontólogo con ese número de colegiatura");
        }

        if (!odontologo.getUsuario().getIdUsuario().equals(request.getIdUsuario())
                && odontologoRepository.existsByUsuario_IdUsuario(request.getIdUsuario())) {
            throw new BadRequestException("Ese usuario ya está asignado a un odontólogo");
        }

        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.getIdUsuario()));

        Especialidad especialidad = especialidadRepository.findById(request.getIdEspecialidad())
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id: " + request.getIdEspecialidad()));

        odontologo.setUsuario(usuario);
        odontologo.setEspecialidad(especialidad);
        odontologo.setNumeroColegiatura(request.getNumeroColegiatura());
        odontologo.setEstado(request.getEstado());

        return odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminar(Integer id) {
        Odontologo odontologo = obtenerPorId(id);
        odontologoRepository.delete(odontologo);
    }
}