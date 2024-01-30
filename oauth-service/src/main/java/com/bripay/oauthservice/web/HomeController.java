package com.bripay.oauthservice.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public String root(Authentication authentication) {
        return "Welcome " + authentication.getName() + " - " + authentication.getAuthorities().toString() + "Pricipal: " + authentication.getPrincipal().toString() + "Details: " + authentication.getDetails().toString() + "Credentials: " + authentication.getCredentials().toString();
    }

    @GetMapping("/api/v1/home")
    public String publico() {
        return "¡Hola, este es un endpoint público!";
    }

    @GetMapping("/api/v1/home/protegido")
    public String protegido() {
        return "¡Hola, este endpoint requiere autenticación!";
    }

    @GetMapping("/api/v1/home/admin")
    public String admin() {
        return "¡Hola, este endpoint requiere permiso de administrador!";
    }
}
