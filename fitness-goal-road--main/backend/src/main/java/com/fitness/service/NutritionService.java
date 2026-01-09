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

        double proteins = user.getPoids() * 2.0;
        double fats = (tdee * 0.25) / 9;
        double carbs = (tdee - (proteins * 4 + fats * 9)) / 4;

        user.setBmr(bmr);
        user.setTdee(tdee);
        user.setBmi(user.getPoids() / (user.getTaille() * user.getTaille()));

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
