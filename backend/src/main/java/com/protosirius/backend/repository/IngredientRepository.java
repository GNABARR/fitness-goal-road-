package com.protosirius.backend.repository;

import com.protosirius.backend.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findTop3ByOrderByProteinsPer100gDesc();
    List<Ingredient> findTop3ByOrderByCarbsPer100gDesc();
    List<Ingredient> findTop1ByOrderByFatsPer100gDesc();
}
