package com.fitness.DemoFitnessArtifact;
import com.fitness.model.Macronutrients;
import com.fitness.service.NutritionService;
import com.fitness.service.QuizResult;
import com.fitness.service.QuizService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.util.Scanner;
@SpringBootApplication
public class FitnessDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessDemoApplication.class, args);



		QuizService quiz = new QuizService();
		NutritionService service = new NutritionService();

		QuizResult result = quiz.run();




		Macronutrients macros = service.calculate(
				result.user(),
				result.activityLevel(),
				result.goal()
		);




		System.out.println("\n=== Résultat nutritionnel Initial ===");
		System.out.println(macros);



		System.out.println("\n--- Début de la simulation (10 Jours) ---");
		System.out.println("Appuyez sur [ENTRÉE] pour accélérer le temps...");





		Thread inputThread = new Thread(() -> {
			try {
				System.in.read();

			} catch (IOException e) {
				e.printStackTrace();
			}
		});


		inputThread.start();






		for (int i = 10; i > 0; i--) {
			if (!inputThread.isAlive()) {
				System.out.println("\rAccélération temporelle activée !        ");
				break;
			}




			System.out.print("\rJours restants : " + i + "   ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
			}



		}

		System.out.println("\n\n--- Fin de la période de 10 jours ---");

		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez votre nouveau poids après ces 10 jours (kg) : ");
		double newWeight = sc.nextDouble();

		Macronutrients newMacros = service.adjust(
				result.user(),
				result.goal(),
				newWeight
		);




		if (newMacros != null) {
			System.out.println("\n=== Nouveau plan nutritionnel adapté ===");
			System.out.println(newMacros);
		} else {
			System.out.println("\n=== Aucun changement nécessaire ===");
			System.out.println("Continuez avec les macros actuelles.");
		}



	}
}