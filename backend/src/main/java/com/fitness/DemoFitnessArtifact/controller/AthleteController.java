package com.fitness.DemoFitnessArtifact.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.fitness.DemoFitnessArtifact.dto.CreateAthleteRequest;
import com.fitness.DemoFitnessArtifact.entity.Athlete;
import com.fitness.DemoFitnessArtifact.repository.AthleteRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/athletes")
public class AthleteController {

    private AthleteRepository athleteRepo;

    public AthleteController(AthleteRepository athleteRepo) {
        this.athleteRepo = athleteRepo;
    }

    @PostMapping
    public Athlete create(@RequestBody CreateAthleteRequest req) {
        Athlete a = new Athlete();
        a.setFirstName(req.firstName);
        a.setLastName(req.lastName);
        a.setWeightKg(req.weightKg);
        return athleteRepo.save(a);
    }

    @GetMapping("/{id}")
    public Athlete get(@PathVariable Long id) {
        return athleteRepo.findById(id).orElseThrow(() -> new RuntimeException("Athlete not found"));
    }
}
