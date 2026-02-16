package com.fitness.DemoFitnessArtifact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NutritionResponse {
    private Integer calories;
    private Integer protein;
    private Integer carbs;
    private Integer fats;
    private Integer bmr;
    private Integer tdee;
}