package com.fitness.DemoFitnessArtifact.config;

import com.fitness.DemoFitnessArtifact.entity.Exercise;
import com.fitness.DemoFitnessArtifact.entity.MuscleGroup;
import com.fitness.DemoFitnessArtifact.repository.ExerciseRepository;
import com.fitness.DemoFitnessArtifact.repository.MuscleGroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seed(MuscleGroupRepository muscleRepo, ExerciseRepository exRepo) {
        return args -> {
            if (muscleRepo.count() > 0 || exRepo.count() > 0) return;

            MuscleGroup chest = muscleRepo.save(new MuscleGroup("Chest (Pectoraux)"));
            MuscleGroup back = muscleRepo.save(new MuscleGroup("Back (Dos)"));
            MuscleGroup lats = muscleRepo.save(new MuscleGroup("Lats (Grand dorsal)"));
            MuscleGroup shoulders = muscleRepo.save(new MuscleGroup("Shoulders (Epaules)"));
            MuscleGroup quadriceps = muscleRepo.save(new MuscleGroup("Quadriceps"));
            MuscleGroup hamstrings = muscleRepo.save(new MuscleGroup("Hamstrings (Ischios)"));
            MuscleGroup glutes = muscleRepo.save(new MuscleGroup("Glutes (Fessiers)"));
            MuscleGroup biceps = muscleRepo.save(new MuscleGroup("Biceps"));
            MuscleGroup triceps = muscleRepo.save(new MuscleGroup("Triceps"));
            MuscleGroup calves = muscleRepo.save(new MuscleGroup("Calves (Mollets)"));
            MuscleGroup abs = muscleRepo.save(new MuscleGroup("Abs / Core"));

            exRepo.save(new Exercise("Barbell Bench Press", 3.5, chest));
            exRepo.save(new Exercise("Incline Bench Press", 3.5, chest));
            exRepo.save(new Exercise("Dumbbell Flyes", 3.5, chest));

            exRepo.save(new Exercise("Deadlift", 6.0, back));
            exRepo.save(new Exercise("Bent-Over Barbell Row", 5.0, back));
            exRepo.save(new Exercise("Pull-Up / Chin-Up", 6.0, back));

            exRepo.save(new Exercise("Pull-Up", 6.0, lats));
            exRepo.save(new Exercise("Lat Pulldown", 5.0, lats));

            exRepo.save(new Exercise("Overhead Press / Military Press", 4.0, shoulders));
            exRepo.save(new Exercise("Lateral Raise", 3.5, shoulders));

            exRepo.save(new Exercise("Barbell Back Squat", 5.0, quadriceps));
            exRepo.save(new Exercise("Leg Press", 4.5, quadriceps));

            exRepo.save(new Exercise("Romanian Deadlift", 5.5, hamstrings));
            exRepo.save(new Exercise("Lying Leg Curl", 3.5, hamstrings));

            exRepo.save(new Exercise("Hip Thrust", 4.5, glutes));

            exRepo.save(new Exercise("Barbell Curl", 3.5, biceps));
            exRepo.save(new Exercise("Dumbbell Hammer Curl", 3.5, biceps));

            exRepo.save(new Exercise("Close-Grip Bench Press", 4.0, triceps));
            exRepo.save(new Exercise("Tricep Dips", 6.0, triceps));
            exRepo.save(new Exercise("Skull Crushers", 3.5, triceps));

            exRepo.save(new Exercise("Standing Calf Raise", 3.0, calves));
            exRepo.save(new Exercise("Seated Calf Raise", 3.0, calves));

            exRepo.save(new Exercise("Hanging Leg Raise", 4.0, abs));
            exRepo.save(new Exercise("Plank", 3.0, abs));
            exRepo.save(new Exercise("Cable Crunch", 3.5, abs));
        };
    }
}
