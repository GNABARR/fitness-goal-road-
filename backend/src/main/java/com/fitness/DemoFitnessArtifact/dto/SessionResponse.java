package com.fitness.DemoFitnessArtifact.dto;

import java.time.LocalDate;
import java.util.List;

public class SessionResponse {
    public Long id;
    public LocalDate sessionDate;
    public double totalCalories;
    public List<Item> items;

    public static class Item {
        public Long exerciseId;
        public String exerciseName;
        public int durationMinutes;
        public String intensity;
        public double caloriesBurned;
    }
}
