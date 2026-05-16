package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void enviarCorreoCita(
            String destino,
            String paciente,
            String fecha,
            String hora,
            String odontologo,
            String sede,
            String consultorio
    ) {

        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setTo(destino);

        mensaje.setSubject("🦷 Confirmación de Cita Odontológica");

        mensaje.setText(
                "Hola " + paciente + ",\n\n" +

                        "Tu cita odontológica fue registrada correctamente.\n\n" +

                        "📅 Fecha: " + fecha + "\n" +
                        "⏰ Hora: " + hora + "\n" +
                        "👨‍⚕️ Odontólogo: " + odontologo + "\n" +
                        "🏥 Sede: " + sede + "\n" +
                        "🪑 Consultorio: " + consultorio + "\n\n" +

                        "⚠️ Recuerda llegar 10 minutos antes de tu cita.\n\n" +

                        "Gracias por confiar en OdontoNova 🦷"
        );

        mailSender.send(mensaje);
    }
}