package com.fitness.service;

import com.fitness.model.ActivityLevel;
import com.fitness.model.FitnessGoal;
import com.fitness.model.Sex;
import com.fitness.model.User;

import java.util.Date;
import java.util.Scanner;

public class QuizService {

    public Record run() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Poids (kg): ");
        double poids = sc.nextDouble();

        System.out.print("Taille (m): ");
        double taille = sc.nextDouble();

        User user = new User(
                "Demo",
                "User",
                "demo@mail.com",
                "password",
                new Date(90, 0, 1),
                Sex.MALE,
                poids,
                taille
        );

        System.out.println("Niveau activité (1: SEDENTAIRE, 2: MODERE, 3: ACTIF)");
        ActivityLevel activity = ActivityLevel.values()[sc.nextInt() - 1];

        System.out.println("Objectif (1: GAIN_POIDS, 2: GAIN_MASSE, 3: PERTE_POIDS, 4: PERTE_GRASSE)");
        FitnessGoal goal = FitnessGoal.values()[sc.nextInt() - 1];



        return new QuizResult(user, activity, goal);



    }
}
