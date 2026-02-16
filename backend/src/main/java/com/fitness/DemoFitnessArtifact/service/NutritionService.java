package com.fitness.DemoFitnessArtifact.service;


import com.fitness.DemoFitnessArtifact.dto.NutritionResponse;
import com.fitness.DemoFitnessArtifact.model.User;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;

@Service
public class NutritionService {

    public NutritionResponse calculateNutrition(User user) {
        int age = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
        
        
        int bmr = (int) Math.round(
            10 * user.getWeight() + 
            6.25 * user.getHeight() - 
            5 * age + 5
        );
        
        
        int tdee = (int) Math.round(bmr * user.getActivityLevel());
        
        
        int calories = tdee;
        if ("cutting".equals(user.getGoal())) {
            calories = tdee - 500;
        } else if ("bulking".equals(user.getGoal())) {
            calories = tdee + 300;
        }
        
        
        int protein = (int) Math.round(user.getWeight() * 2.2);
        int fats = (int) Math.round((calories * 0.25) / 9);
        int carbs = (int) Math.round((calories - (protein * 4) - (fats * 9)) / 4);
        
        return new NutritionResponse(calories, protein, carbs, fats, bmr, tdee);
    }
}
