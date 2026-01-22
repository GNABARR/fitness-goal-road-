package com.fitness.DemoFitnessArtifact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String gender;
    private Double weight;
    private Double height;
    private Double activityLevel;
    private String goal;
}
