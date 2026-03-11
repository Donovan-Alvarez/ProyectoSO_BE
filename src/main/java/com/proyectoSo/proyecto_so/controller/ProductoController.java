package com.proyectoSo.proyecto_so.controller;

import com.proyectoSo.proyecto_so.model.Producto;
import com.proyectoSo.proyecto_so.repository.ProductoRespository;
import com.proyectoSo.proyecto_so.service.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Producto crear(@RequestBody Producto producto){
        return productoRespository.save(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto){

        Producto p = productoRespository.findById(id).orElseThrow();

        p.setNombre(producto.getNombre());
        p.setPrecio(producto.getPrecio());

        return productoRespository.save(p);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        productoRespository.deleteById(id);
    }

}
