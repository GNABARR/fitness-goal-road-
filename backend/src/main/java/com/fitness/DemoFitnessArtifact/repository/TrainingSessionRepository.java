package com.fitness.DemoFitnessArtifact.repository;

import com.fitness.DemoFitnessArtifact.entity.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByAthleteIdOrderBySessionDateDesc(Long athleteId);
}
