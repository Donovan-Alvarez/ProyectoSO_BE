package com.proyectoSo.proyecto_so.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String nombre;

    String descripcion;

    Double precio;

    Integer stock;

    String imagen;
}
