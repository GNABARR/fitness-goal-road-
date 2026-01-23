package com.fitness.DemoFitnessArtifact.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_session")
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="athlete_id", nullable=false)
    private Athlete athlete;

    @Column(nullable=false)
    private LocalDate sessionDate;

    @Column(nullable=false)
    private double totalCalories;

    @OneToMany(mappedBy="session", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<SessionExercise> exercises = new ArrayList<SessionExercise>();

    public TrainingSession() {}

    public Long getId() { return id; }
    public Athlete getAthlete() { return athlete; }
    public LocalDate getSessionDate() { return sessionDate; }
    public double getTotalCalories() { return totalCalories; }
    public List<SessionExercise> getExercises() { return exercises; }

    public void setId(Long id) { this.id = id; }
    public void setAthlete(Athlete athlete) { this.athlete = athlete; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }
    public void setTotalCalories(double totalCalories) { this.totalCalories = totalCalories; }
    public void setExercises(List<SessionExercise> exercises) { this.exercises = exercises; }
}
