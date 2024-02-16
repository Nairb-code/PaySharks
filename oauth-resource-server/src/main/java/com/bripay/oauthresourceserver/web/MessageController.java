package com.bripay.oauthresourceserver.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello from resource server";
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
