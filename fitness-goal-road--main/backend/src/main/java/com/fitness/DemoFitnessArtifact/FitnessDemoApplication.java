package com.fitness.DemoFitnessArtifact;

import com.fitness.model.Macronutrients;
import com.fitness.model.Sex;
import com.fitness.model.User;
import com.fitness.service.NutritionService;
import com.fitness.service.QuizResult;
import com.fitness.service.QuizService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class FitnessDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessDemoApplication.class, args);

//		User user1 = new User(
//				"Gnabar",
//				"Ismail",
//				"timothywright@example.net",
//				"password123",
//				new Date(),     // date actuelle, simple et valide
//				Sex.FEMALE,
//				73.9,
//				1.56
//		);


		QuizService quiz = new QuizService();
		NutritionService service = new NutritionService();

		QuizResult result = (QuizResult) quiz.run();

		Macronutrients macros = service.calculate(
				result.user(),
				result.activityLevel(),
				result.goal()
		);

		System.out.println("\n=== Résultat nutritionnel ===");
		System.out.println(macros);


	}

}
