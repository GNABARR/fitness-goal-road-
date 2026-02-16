package com.fitness.DemoFitnessArtifact.controller;

import com.fitness.DemoFitnessArtifact.dto.NutritionResponse;
import com.fitness.DemoFitnessArtifact.model.User;
import com.fitness.DemoFitnessArtifact.service.NutritionService;
import com.fitness.DemoFitnessArtifact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = "http://localhost:5173")
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<NutritionResponse> getNutrition(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            NutritionResponse response = nutritionService.calculateNutrition(user);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}