package com.fitness.DemoFitnessArtifact;
import jakarta.persistence.*;

@Entity
@Table(name = "muscle_group")
public class MuscleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String name; // ex: Chest, Back, Biceps...

    public MuscleGroup() {}

    public MuscleGroup(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
}
