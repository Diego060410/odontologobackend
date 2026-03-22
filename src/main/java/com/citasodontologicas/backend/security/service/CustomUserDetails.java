package com.citasodontologicas.backend.security.service;

import com.citasodontologicas.backend.entity.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rolNombre = usuario.getRol().getNombreRol();
        return List.of(new SimpleGrantedAuthority("ROLE_" + rolNombre.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return usuario.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return usuario.getEstado();
    }

    @Override
    public boolean isAccountNonLocked() {
        return usuario.getEstado();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return usuario.getEstado();
    }

    @Override
    public boolean isEnabled() {
        return usuario.getEstado();
    }

    public Usuario getUsuario() {
        return usuario;
    }
}