package com.fitness.DemoFitnessArtifact.entity;

import com.fitness.DemoFitnessArtifact.enums.IntensityLevel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="session_exercise")
public class SessionExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="session_id", nullable=false)
    private TrainingSession session;

    @ManyToOne
    @JoinColumn(name="exercise_id", nullable=false)
    private Exercise exercise;

    @Column(nullable=false)
    private int durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private IntensityLevel intensity;

    @Column(nullable=false)
    private double caloriesBurned;

    public SessionExercise() {}

    public Long getId() { return id; }
    public TrainingSession getSession() { return session; }
    public Exercise getExercise() { return exercise; }
    public int getDurationMinutes() { return durationMinutes; }
    public IntensityLevel getIntensity() { return intensity; }
    public double getCaloriesBurned() { return caloriesBurned; }

    public void setId(Long id) { this.id = id; }
    public void setSession(TrainingSession session) { this.session = session; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public void setIntensity(IntensityLevel intensity) { this.intensity = intensity; }
    public void setCaloriesBurned(double caloriesBurned) { this.caloriesBurned = caloriesBurned; }
}
