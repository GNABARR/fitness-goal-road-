package com.fitness.DemoFitnessArtifact.repository;

import com.fitness.DemoFitnessArtifact.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {}
