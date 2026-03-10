package com.proyectoSo.proyecto_so.repository;

import com.proyectoSo.proyecto_so.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRespository extends JpaRepository<Producto, Long> {
}
