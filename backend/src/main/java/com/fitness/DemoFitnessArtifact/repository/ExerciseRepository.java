package com.fitness.DemoFitnessArtifact.repository;

import com.fitness.DemoFitnessArtifact.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByMuscleGroupId(Long muscleGroupId);
}
