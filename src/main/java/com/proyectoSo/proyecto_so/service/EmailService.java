package com.proyectoSo.proyecto_so.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }
    @Async
    public void enviarCorreo(String destino, String asunto, String mensaje){

        try {

            SimpleMailMessage email = new SimpleMailMessage();

            email.setTo(destino);
            email.setSubject(asunto);
            email.setText(mensaje);

            mailSender.send(email);

            System.out.println("Correo enviado a: " + destino);

        } catch (Exception e) {

            System.out.println("Error enviando correo: " + e.getMessage());

        }
    }
}
