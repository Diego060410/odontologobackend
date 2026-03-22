package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.auth.AuthRequest;
import com.citasodontologicas.backend.dto.auth.RegisterRequest;
import com.citasodontologicas.backend.dto.response.AuthResponse;
import com.citasodontologicas.backend.entity.Rol;
import com.citasodontologicas.backend.entity.Usuario;
import com.citasodontologicas.backend.repository.RolRepository;
import com.citasodontologicas.backend.repository.UsuarioRepository;
import com.citasodontologicas.backend.security.jwt.JwtService;
import com.citasodontologicas.backend.security.service.CustomUserDetails;
import com.citasodontologicas.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Usuario o contraseña incorrectos");
        }

        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Usuario no encontrado"));

        CustomUserDetails userDetails = new CustomUserDetails(usuario);
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .username(usuario.getUsername())
                .rol(usuario.getRol().getNombreRol())
                .build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El username ya está registrado");
        }

        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        if (usuarioRepository.existsByDocumentoIdentidad(request.getDocumentoIdentidad())) {
            throw new IllegalArgumentException("El documento de identidad ya está registrado");
        }

        Rol rol = rolRepository.findByNombreRol(request.getNombreRol())
                .orElseThrow(() -> new IllegalArgumentException("El rol no existe: " + request.getNombreRol()));

        Usuario usuario = new Usuario();
        usuario.setRol(rol);
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setDocumentoIdentidad(request.getDocumentoIdentidad());
        usuario.setCorreo(request.getCorreo());
        usuario.setTelefono(request.getTelefono());
        usuario.setUsername(request.getUsername());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setEstado(true);
        usuario.setFechaRegistro(LocalDateTime.now());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        CustomUserDetails userDetails = new CustomUserDetails(usuarioGuardado);
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .username(usuarioGuardado.getUsername())
                .rol(usuarioGuardado.getRol().getNombreRol())
                .build();
    }
}