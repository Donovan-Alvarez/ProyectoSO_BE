package com.proyectoSo.proyecto_so.security;

import com.proyectoSo.proyecto_so.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    public JwtAuthenticationFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            String email = jwtService.extractUserName(token);

            if (email != null && jwtService.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_USER")) // DALE UN ROL MANUAL
                        );

                // IMPORTANTE: Establecemos los detalles de la petición
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // ESTA ES LA LÍNEA CLAVE: Registramos al usuario en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("DEBUG: Usuario " + email + " autenticado correctamente.");
            }
        } catch (Exception e) {
            System.out.println("DEBUG: Error procesando el token: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}