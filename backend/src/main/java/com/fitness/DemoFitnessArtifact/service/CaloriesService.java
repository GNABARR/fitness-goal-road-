package com.fitness.DemoFitnessArtifact.service;

import com.fitness.DemoFitnessArtifact.enums.IntensityLevel;
import org.springframework.stereotype.Service;

@Service
public class CaloriesService {

    public double factor(IntensityLevel intensity) {
        if (intensity == IntensityLevel.LOW) return 0.8;
        if (intensity == IntensityLevel.HIGH) return 1.2;
        return 1.0;
    }

    public double calculate(double metBase, double weightKg, int durationMinutes, IntensityLevel intensity) {
        double metAdjusted = metBase * factor(intensity);
        return metAdjusted * weightKg * (durationMinutes / 60.0);
    }
}
