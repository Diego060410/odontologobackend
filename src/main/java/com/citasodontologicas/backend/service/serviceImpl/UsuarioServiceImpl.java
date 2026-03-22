package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.UsuarioRequest;
import com.citasodontologicas.backend.entity.Rol;
import com.citasodontologicas.backend.entity.Usuario;
import com.citasodontologicas.backend.exception.BadRequestException;
import com.citasodontologicas.backend.exception.ResourceNotFoundException;
import com.citasodontologicas.backend.repository.RolRepository;
import com.citasodontologicas.backend.repository.UsuarioRepository;
import com.citasodontologicas.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario guardar(UsuarioRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("El username ya está registrado");
        }

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new BadRequestException("El correo ya está registrado");
        }

        if (usuarioRepository.existsByDocumentoIdentidad(request.getDocumentoIdentidad())) {
            throw new BadRequestException("El documento de identidad ya está registrado");
        }

        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id: " + request.getIdRol()));

        Usuario usuario = Usuario.builder()
                .rol(rol)
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .documentoIdentidad(request.getDocumentoIdentidad())
                .correo(request.getCorreo())
                .telefono(request.getTelefono())
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .estado(request.getEstado())
                .fechaRegistro(LocalDateTime.now())
                .build();

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(Integer id, UsuarioRequest request) {
        Usuario usuario = obtenerPorId(id);

        if (!usuario.getUsername().equalsIgnoreCase(request.getUsername())
                && usuarioRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("El username ya está registrado");
        }

        if (!usuario.getCorreo().equalsIgnoreCase(request.getCorreo())
                && usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new BadRequestException("El correo ya está registrado");
        }

        if (!usuario.getDocumentoIdentidad().equalsIgnoreCase(request.getDocumentoIdentidad())
                && usuarioRepository.existsByDocumentoIdentidad(request.getDocumentoIdentidad())) {
            throw new BadRequestException("El documento de identidad ya está registrado");
        }

        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id: " + request.getIdRol()));

        usuario.setRol(rol);
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setDocumentoIdentidad(request.getDocumentoIdentidad());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setUsername(request.getUsername());

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        usuario.setEstado(request.getEstado());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminar(Integer id) {
        Usuario usuario = obtenerPorId(id);
        usuarioRepository.delete(usuario);
    }
}
