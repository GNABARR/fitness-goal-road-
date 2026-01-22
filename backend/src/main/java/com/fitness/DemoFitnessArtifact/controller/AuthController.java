package com.fitness.DemoFitnessArtifact.controller;

import com.fitness.DemoFitnessArtifact.dto.LoginRequest;
import com.fitness.DemoFitnessArtifact.dto.LoginResponse;
import com.fitness.DemoFitnessArtifact.model.User;
import com.fitness.DemoFitnessArtifact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> user = userService.authenticate(request.getEmail(), request.getPassword());
        
        if (user.isPresent()) {
            User u = user.get();
            LoginResponse response = new LoginResponse(
                u.getId(),
                u.getFirstName(),
                u.getLastName(),
                u.getEmail(),
                u.getDateOfBirth().toString(),
                u.getGender(),
                u.getWeight(),
                u.getHeight(),
                u.getActivityLevel(),
                u.getGoal()
            );
            return ResponseEntity.ok(response);
        }
        
        return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
    }
}
