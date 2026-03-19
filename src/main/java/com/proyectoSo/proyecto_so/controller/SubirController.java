package com.proyectoSo.proyecto_so.controller;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;

@RestController
@RequestMapping("/api/subir")
public class SubirController {

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String nombreArchivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path ruta = Paths.get("subidos").resolve(nombreArchivo);

            Files.copy(file.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("/subidos/" + nombreArchivo);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir archivo");
        }
    }

    @GetMapping("/imagen/{nombre}")
    public ResponseEntity<Resource> verImagen(@PathVariable String nombre) {
        try {
            Path ruta = Paths.get("subidos").resolve(nombre);
            Resource recurso = new UrlResource(ruta.toUri());

            if (!recurso.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(recurso);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
