package com.fitness.service;

import com.fitness.model.ActivityLevel;
import com.fitness.model.FitnessGoal;
import com.fitness.model.User;

public record QuizResult(User user,
                         ActivityLevel activityLevel,
                         FitnessGoal goal) {
}

