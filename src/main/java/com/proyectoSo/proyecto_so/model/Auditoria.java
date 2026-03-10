package com.proyectoSo.proyecto_so.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auditoria")
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;

    private String accion;

    private String endpoint;

    private String ip;

    private LocalDateTime fecha = LocalDateTime.now();

}
