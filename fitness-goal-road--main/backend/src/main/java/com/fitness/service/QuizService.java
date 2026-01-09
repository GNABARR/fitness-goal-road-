package com.fitness.service;
import com.fitness.model.ActivityLevel;
import com.fitness.model.FitnessGoal;
import com.fitness.model.Sex;
import com.fitness.model.User;
import java.util.Date;
import java.util.Scanner;
public class QuizService {

    public QuizResult run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Configuration du Profil Utilisateur ===");



        System.out.print("votre Année de naissance  : ");
        int year = sc.nextInt();
        Date dateNaissance = new Date(year - 1900, 0, 1);




        System.out.println("Sexe (1: Homme, 2: Femme) : ");
        int sexChoice = sc.nextInt();
        Sex sex = (sexChoice == 2) ? Sex.FEMALE : Sex.MALE;



        System.out.print("Poids actuel (kg): ");
        double poids = sc.nextDouble();



        System.out.print("Taille (m): ");
        double taille = sc.nextDouble();

        User user = new User(
                null,
                null,
                null,
                "password",
                dateNaissance,
                sex,
                poids,
                taille
        );

        System.out.println("\nindiquer votre Niveau d'Activité s'il vous plait");
        System.out.println("1: Sédentaire (Peu ou pas d'exercice)");
        System.out.println("2: Modéré (Exercice 1-3 fois/semaine)");
        System.out.println("3: Actif (Exercice 3-5 fois/semaine)");
        System.out.println("4: Très Actif (Exercice 6-7 fois/semaine)");
        ActivityLevel activity = ActivityLevel.values()[sc.nextInt() - 1];




        System.out.println("\n=== votre Objectif Principal ? ");
        System.out.println("1. Gagner du poids");
        System.out.println("2. Perdre du poids");
        int mainChoice = sc.nextInt();




        FitnessGoal goal = null;





        if (mainChoice == 1) {
            System.out.println("Précisez votre objectif de gain :");
            System.out.println("1. Prise de poids générale");
            System.out.println("2. Prise de masse musculaire");
            int subChoice = sc.nextInt();
            goal = (subChoice == 1) ? FitnessGoal.GAIN_POIDS : FitnessGoal.GAIN_MASSE_MUSCULAIRE;
        } else {
            System.out.println("Précisez votre objectif de perte :");
            System.out.println("1. Perte de poids général");
            System.out.println("2. Perte de gras");
            int subChoice = sc.nextInt();
            goal = (subChoice == 1) ? FitnessGoal.PERTE_POIDS : FitnessGoal.PERTE_GRASSE;
        }

        return new QuizResult(user, activity, goal);
    }
}