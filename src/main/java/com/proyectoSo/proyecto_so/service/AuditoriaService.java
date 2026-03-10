package com.proyectoSo.proyecto_so.service;

import com.proyectoSo.proyecto_so.model.Auditoria;
import com.proyectoSo.proyecto_so.repository.AuditoriaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository){
        this.auditoriaRepository = auditoriaRepository;
    }

    public void registrar(String usuario, String accion, HttpServletRequest request){

        Auditoria auditoria = new Auditoria();

        auditoria.setUsuario(usuario);
        auditoria.setAccion(accion);
        auditoria.setEndpoint(request.getRequestURI());
        auditoria.setIp(request.getRemoteAddr());

        auditoriaRepository.save(auditoria);
    }
}
