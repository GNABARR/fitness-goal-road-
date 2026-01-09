package com.fitness.service;
import com.fitness.model.*;
import java.time.LocalDate;
import java.time.ZoneId;
public class NutritionService {

    public Macronutrients calculate(User user,
                                    ActivityLevel activityLevel,
                                    FitnessGoal goal) {

        int age = calculateAge(user);

        double bmr = 10 * user.getPoids()
                + 6.25 * (user.getTaille() * 100)
                - 5 * age
                + (user.getSex() == Sex.MALE ? 5 : -161);

        double tdee = bmr * activityLevel.getFactor();





        switch (goal) {
            case GAIN_POIDS, GAIN_MASSE_MUSCULAIRE -> tdee += 300;
            case PERTE_POIDS, PERTE_GRASSE -> tdee -= 400;
        }

        user.setBmr(bmr);
        user.setTdee(tdee);
        user.setBmi(user.getPoids() / (user.getTaille() * user.getTaille()));

        return buildMacronutrients(user.getPoids(), tdee);



    }

    public Macronutrients adjust(User user, FitnessGoal goal, double newWeight) {
        double currentTdee = user.getTdee();
        double previousWeight = user.getPoids();
        double weightDiff = newWeight - previousWeight;

        double weeklyRate = weightDiff * (7.0 / 10.0);




        boolean adjustmentNeeded = false;
        String message = "Résultats satisfaisants. Continuez ainsi !";

        if (goal == FitnessGoal.GAIN_POIDS) {
            if (weeklyRate > 0.6) {
                currentTdee -= 200;
                adjustmentNeeded = true;
                message = "Prise de poids trop rapide (> 0.5kg/semaine). Minimiser le défi calorique.";
            } else if (weeklyRate < 0.4) {
                currentTdee += 200;
                adjustmentNeeded = true;




                message = "Prise de poids trop lente (< 0.5kg/semaine). Augmenter le défi calorique.";
            }
        }
        else if (goal == FitnessGoal.GAIN_MASSE_MUSCULAIRE) {
            if (weeklyRate > 0.5) {
                currentTdee -= 150;
                adjustmentNeeded = true;
                message = "Gain rapide (> 0.5kg/semaine), risque de prise de gras. Minimiser le défi.";
            } else if (weeklyRate < 0.25) {
                currentTdee += 150;
                adjustmentNeeded = true;



                message = "Gain musculaire trop lent ou poids stable. Augmenter le défi.";
            }
        }
        else {
            if (weeklyRate < -1.0) {
                currentTdee += 200;
                adjustmentNeeded = true;
                message = "Perte trop rapide. Augmentation des calories par sécurité.";
            } else if (weeklyRate > -0.2) {
                currentTdee -= 200;


                adjustmentNeeded = true;
                message = "Perte trop lente ou stagnation. Réduction des calories.";
            }
        }

        System.out.println("\nBilan : " + message);

        if (!adjustmentNeeded) {
            return null;
        }

        user.setPoids(newWeight);
        user.setTdee(currentTdee);

        return buildMacronutrients(newWeight, currentTdee);
    }

    private Macronutrients buildMacronutrients(double weight, double tdee) {
        double proteins = weight * 2.0;
        double fats = (tdee * 0.25) / 9;

        double carbs = (tdee - (proteins * 4 + fats * 9)) / 4;

        return new Macronutrients(tdee, proteins, carbs, fats);
    }

    private int calculateAge(User user) {
        LocalDate birthDate = user.getDateNaissance()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return LocalDate.now().getYear() - birthDate.getYear();
    }
}