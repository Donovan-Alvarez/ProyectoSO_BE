package com.proyectoSo.proyecto_so.service;

import com.proyectoSo.proyecto_so.dto.LoginRequest;
import com.proyectoSo.proyecto_so.dto.RegisterRequest;
import com.proyectoSo.proyecto_so.model.Usuario;
import com.proyectoSo.proyecto_so.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final EmailService emailService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                       JWTService jwtService,
                       EmailService emailService){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public ResponseEntity<Usuario> register(RegisterRequest registerRequest) throws Exception {
        try{
            Usuario usuario = new Usuario();
            StringBuilder detalleCorreo = new StringBuilder();
            detalleCorreo.append("Registro de usuario");

            usuario.setNombre(registerRequest.getNombre());
            usuario.setEmail(registerRequest.getEmail());
            usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            usuario.setRol("USER");

            detalleCorreo.append(" Bievenido: " + registerRequest.getNombre());

            emailService.enviarCorreo(
                    usuario.getEmail(),
                    "Registro de usuario " + registerRequest.getEmail(),
                    detalleCorreo.toString()
            );

            return ResponseEntity.ok(usuarioRepository.save(usuario));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public String login(String email, String password){
        Usuario usuario = new Usuario();
        usuario = usuarioRepository.findByEmail(email);
        if(usuario != null){
            if(passwordEncoder.matches(password, usuario.getPassword())){
                return jwtService.generatedToken(usuario.getEmail());
            }else{
                throw new RuntimeException("Password incorrecta");
            }
        }else{
            throw new RuntimeException("Usuario no encontrado");
        }

    }
}
