package com.bripay.oauthservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @GetMapping
    public String publico() {
        return "¡Hola, este es un endpoint público!";
    }

    @GetMapping("/protegido")
    public String protegido() {
        return "¡Hola, este endpoint requiere autenticación!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "¡Hola, este endpoint requiere permiso de administrador!";
    }
}
