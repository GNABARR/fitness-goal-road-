package com.protosirius.backend.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.protosirius.backend.entity.IngredientQuantite;

class PlanAlimentaireServiceTest {

    private final PlanAlimentaireService planAlimentaireService = new PlanAlimentaireService(null, null);

    @Test
    void corrigerVersCible_shouldScaleProteinesToMatchTarget() {
        List<IngredientQuantite> collection = List.of(
                new IngredientQuantite("Poulet", 100, 20, 0, 5, 165)
        );


        double cibleProteines = 40;
        List<IngredientQuantite> resultat = planAlimentaireService.corrigerVersCible(collection, cibleProteines, true);
        assertEquals(40, resultat.get(0).getProteines(), 0.01);}
}
