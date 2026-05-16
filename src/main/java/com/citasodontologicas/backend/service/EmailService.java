package com.citasodontologicas.backend.service;

public interface EmailService {

    void enviarCorreoCita(
            String destino,
            String paciente,
            String fecha,
            String hora,
            String odontologo,
            String sede,
            String consultorio
    );
}