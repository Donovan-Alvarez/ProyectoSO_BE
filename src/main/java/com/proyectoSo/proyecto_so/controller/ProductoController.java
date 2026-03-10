package com.proyectoSo.proyecto_so.controller;

import com.proyectoSo.proyecto_so.model.Producto;
import com.proyectoSo.proyecto_so.repository.ProductoRespository;
import com.proyectoSo.proyecto_so.service.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/producto")
@CrossOrigin
public class ProductoController {

    private final ProductoRespository productoRespository;
    private final AuditoriaService auditoriaService;

    public ProductoController(ProductoRespository productoRespository, AuditoriaService auditoriaService) {
        this.productoRespository = productoRespository;
        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProducts(HttpServletRequest httpServletRequest) {
        String usuario = Objects.requireNonNull(SecurityContextHolder.
                        getContext()
                        .getAuthentication())
                .getName();
        auditoriaService.registrar(usuario, "VER_PRODUCTO", httpServletRequest);
        return ResponseEntity.ok(productoRespository.findAll());

    }

}
