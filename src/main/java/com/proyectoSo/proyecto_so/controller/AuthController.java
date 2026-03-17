package com.proyectoSo.proyecto_so.controller;

import com.proyectoSo.proyecto_so.dto.LoginRequest;
import com.proyectoSo.proyecto_so.dto.LoginResponse;
import com.proyectoSo.proyecto_so.dto.RegisterRequest;
import com.proyectoSo.proyecto_so.model.Usuario;
import com.proyectoSo.proyecto_so.service.AuditoriaService;
import com.proyectoSo.proyecto_so.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "https://tienda.grupo2.os")
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final AuditoriaService auditoriaService;

    public AuthController(AuthService authService, AuditoriaService auditoriaService){
        this.authService = authService;
        this.auditoriaService = auditoriaService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Usuario> register(
            @RequestBody @Valid RegisterRequest request)
            throws Exception{
        return authService.register(request);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request, HttpServletRequest httpServletRequest){
        var token = authService.login(request.getEmail(), request.getPassword());

        auditoriaService.registrar(request.getEmail(),
                "LOGIN", httpServletRequest);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
