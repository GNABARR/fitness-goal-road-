package com.fitness.DemoFitnessArtifact.controller;

import com.fitness.DemoFitnessArtifact.entity.Exercise;
import com.fitness.DemoFitnessArtifact.entity.MuscleGroup;
import com.fitness.DemoFitnessArtifact.repository.ExerciseRepository;
import com.fitness.DemoFitnessArtifact.repository.MuscleGroupRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/muscles")
public class MuscleController {

    private MuscleGroupRepository muscleRepo;
    private ExerciseRepository exerciseRepo;

    public MuscleController(MuscleGroupRepository muscleRepo, ExerciseRepository exerciseRepo) {
        this.muscleRepo = muscleRepo;
        this.exerciseRepo = exerciseRepo;
    }

    @GetMapping
    public List<MuscleGroup> muscles() {
        return muscleRepo.findAll();
    }

    @GetMapping("/{muscleId}/exercises")
    public List<Exercise> exercises(@PathVariable Long muscleId) {
        return exerciseRepo.findByMuscleGroupId(muscleId);
    }
}
