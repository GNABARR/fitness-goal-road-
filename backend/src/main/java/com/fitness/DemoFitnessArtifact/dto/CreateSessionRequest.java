package com.fitness.DemoFitnessArtifact.dto;

import com.fitness.DemoFitnessArtifact.enums.IntensityLevel;
import java.time.LocalDate;
import java.util.List;

public class CreateSessionRequest {
    public LocalDate sessionDate;
    public List<Item> items;

    public static class Item {
        public Long exerciseId;
        public int durationMinutes;
        public IntensityLevel intensity;
    }
}
