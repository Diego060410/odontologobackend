package com.citasodontologicas.backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Endpoint público funcionando";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "Endpoint privado funcionando con JWT";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Endpoint solo para ADMIN";
    }
}