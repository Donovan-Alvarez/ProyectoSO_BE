package com.proyectoSo.proyecto_so.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

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

    public void enviarCorreoConAdjunto(String destino, String asunto, String mensaje, String archivo){

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try{

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(destino);
            helper.setSubject(asunto);
            helper.setText(mensaje);

            FileSystemResource file = new FileSystemResource(new File(archivo));

            helper.addAttachment(file.getFilename(), file);

            mailSender.send(mimeMessage);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}






