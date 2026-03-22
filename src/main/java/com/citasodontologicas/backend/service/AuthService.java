package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.auth.AuthRequest;
import com.citasodontologicas.backend.dto.auth.RegisterRequest;
import com.citasodontologicas.backend.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    AuthResponse register(RegisterRequest request);
}