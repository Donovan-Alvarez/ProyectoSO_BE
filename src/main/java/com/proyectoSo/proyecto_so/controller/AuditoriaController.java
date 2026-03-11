package com.proyectoSo.proyecto_so.controller;

import com.proyectoSo.proyecto_so.model.Auditoria;
import com.proyectoSo.proyecto_so.repository.AuditoriaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaController(AuditoriaRepository auditoriaRepository){
        this.auditoriaRepository = auditoriaRepository;
    }

    @GetMapping
    public List<Auditoria> listar(){
        return auditoriaRepository.findAll();
    }
}