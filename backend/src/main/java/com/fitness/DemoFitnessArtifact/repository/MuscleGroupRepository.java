package com.fitness.DemoFitnessArtifact.repository;

import com.fitness.DemoFitnessArtifact.entity.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
    Optional<MuscleGroup> findByName(String name);
}
