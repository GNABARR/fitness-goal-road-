package com.fitness.DemoFitnessArtifact.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private double metBase;

    @ManyToOne
    @JoinColumn(name = "muscle_group_id", nullable=false)
    private MuscleGroup muscleGroup;

    public Exercise() {}

    public Exercise(String name, double metBase, MuscleGroup muscleGroup) {
        this.name = name;
        this.metBase = metBase;
        this.muscleGroup = muscleGroup;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getMetBase() { return metBase; }
    public MuscleGroup getMuscleGroup() { return muscleGroup; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMetBase(double metBase) { this.metBase = metBase; }
    public void setMuscleGroup(MuscleGroup muscleGroup) { this.muscleGroup = muscleGroup; }
}
