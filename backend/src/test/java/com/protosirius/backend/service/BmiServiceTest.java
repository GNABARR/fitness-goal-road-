package com.protosirius.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BmiServiceTest {

    private final BmiService bmiService = new BmiService(null);

    @Test
    void categoriserBMI_shouldReturnPoidsNormal_whenBmiIsInNormalRange() {


        double bmi = 22.0;
        String categorie = bmiService.categoriserBMI(bmi);
        assertEquals("Poids normal", categorie);}




    @Test
    void categoriserBMI_shouldReturnSurpoids_whenBmiIsAbove25() {

        double bmi = 27.0;
        String categorie = bmiService.categoriserBMI(bmi);
        assertEquals("Surpoids", categorie);}
}
