package com.fitness.service;

import com.fitness.model.ActivityLevel;
import com.fitness.model.FitnessGoal;
import com.fitness.model.Sex;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    public ProfilData creerProfilData(QuizRequest request) {
        return new ProfilData(
                request.anneeNaissance(),
                request.sex(),
                request.poids(),
                request.taille(),
                request.activityLevel(),
                request.goal()
        );
    }

    public record QuizRequest(  int anneeNaissance,   Sex sex,   double poids,   double taille,   ActivityLevel activityLevel,   FitnessGoal goal
    ) {}

    public record ProfilData(  int anneeNaissance,   Sex sex,   double poids,   double taille,   ActivityLevel activityLevel,   FitnessGoal goal  ) {}





}