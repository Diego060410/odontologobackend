package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.service.EmailService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceSendGridImpl implements EmailService {

    @Value("${sendgrid.api.key}")
    private String apiKey;

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

        Email from = new Email("odontonova@gmail.com");
        Email to = new Email(destino);

        String subject = "🦷 Confirmación de Cita Odontológica";

        String contentText =
                "Hola " + paciente + ",\n\n" +
                        "Tu cita odontológica fue registrada correctamente.\n\n" +
                        "📅 Fecha: " + fecha + "\n" +
                        "⏰ Hora: " + hora + "\n" +
                        "👨‍⚕️ Odontólogo: " + odontologo + "\n" +
                        "🏥 Sede: " + sede + "\n" +
                        "🪑 Consultorio: " + consultorio + "\n\n" +
                        "Gracias por confiar en OdontoNova 🦷";

        Content content = new Content("text/plain", contentText);

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            sg.api(request);

            System.out.println("✅ Email enviado correctamente");

        } catch (IOException ex) {
            System.err.println("❌ Error enviando email: " + ex.getMessage());
        }
    }
}