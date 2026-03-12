package com.protosirius.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.protosirius.backend.entity.User;
import com.protosirius.backend.service.AuthService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        User user = authService.register(request);
        return AuthResponse.fromUser(user , "Inscription réussie");
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        User user = authService.login(request);
        return AuthResponse.fromUser(user , "Connexion réussie");
    }

}
