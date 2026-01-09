package com.fitness.service;

public class SimulationService {

    public void startSimulation() throws InterruptedException {
        System.out.println("\n--- Début de la simulation (10 jours) ---");

        for (int day = 10; day >= 1; day--) {
            System.out.print("\rJour restant : " + day);
            Thread.sleep(1000); // 1 seconde = 1 jour (démo)
        }

        System.out.println("\nSimulation terminée !");
    }
}
