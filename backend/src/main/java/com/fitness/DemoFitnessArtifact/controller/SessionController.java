package com.fitness.DemoFitnessArtifact.controller;

import com.fitness.DemoFitnessArtifact.dto.CreateSessionRequest;
import com.fitness.DemoFitnessArtifact.dto.SessionResponse;
import com.fitness.DemoFitnessArtifact.entity.*;
import com.fitness.DemoFitnessArtifact.repository.*;
import com.fitness.DemoFitnessArtifact.service.CaloriesService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/athletes/{athleteId}/sessions")
public class SessionController {

    private AthleteRepository athleteRepo;
    private ExerciseRepository exerciseRepo;
    private TrainingSessionRepository sessionRepo;
    private CaloriesService caloriesService;

    public SessionController(AthleteRepository athleteRepo,
                             ExerciseRepository exerciseRepo,
                             TrainingSessionRepository sessionRepo,
                             CaloriesService caloriesService) {
        this.athleteRepo = athleteRepo;
        this.exerciseRepo = exerciseRepo;
        this.sessionRepo = sessionRepo;
        this.caloriesService = caloriesService;
    }

    @PostMapping
    public SessionResponse createSession(@PathVariable Long athleteId,
                                         @RequestBody CreateSessionRequest request) {

        Athlete athlete = athleteRepo.findById(athleteId)
                .orElseThrow(() -> new RuntimeException("Athlete not found"));

        TrainingSession session = new TrainingSession();
        session.setAthlete(athlete);
        session.setSessionDate(request.sessionDate);

        double total = 0.0;
        List<SessionExercise> sessionExercises = new ArrayList<SessionExercise>();

        for (CreateSessionRequest.Item item : request.items) {
            Exercise ex = exerciseRepo.findById(item.exerciseId)
                    .orElseThrow(() -> new RuntimeException("Exercise not found: " + item.exerciseId));

            double burned = caloriesService.calculate(
                    ex.getMetBase(),
                    athlete.getWeightKg(),
                    item.durationMinutes,
                    item.intensity
            );

            SessionExercise se = new SessionExercise();
            se.setSession(session);
            se.setExercise(ex);
            se.setDurationMinutes(item.durationMinutes);
            se.setIntensity(item.intensity);
            se.setCaloriesBurned(burned);

            sessionExercises.add(se);
            total += burned;
        }

        session.setExercises(sessionExercises);
        session.setTotalCalories(total);

        TrainingSession saved = sessionRepo.save(session);

        return toResponse(saved);
    }

    @GetMapping
    public List<SessionResponse> history(@PathVariable Long athleteId) {
        List<TrainingSession> sessions = sessionRepo.findByAthleteIdOrderBySessionDateDesc(athleteId);
        List<SessionResponse> res = new ArrayList<SessionResponse>();
        for (TrainingSession s : sessions) {
            res.add(toResponse(s));
        }
        return res;
    }

    private SessionResponse toResponse(TrainingSession s) {
        SessionResponse r = new SessionResponse();
        r.id = s.getId();
        r.sessionDate = s.getSessionDate();
        r.totalCalories = s.getTotalCalories();

        List<SessionResponse.Item> items = new ArrayList<SessionResponse.Item>();
        for (SessionExercise se : s.getExercises()) {
            SessionResponse.Item it = new SessionResponse.Item();
            it.exerciseId = se.getExercise().getId();
            it.exerciseName = se.getExercise().getName();
            it.durationMinutes = se.getDurationMinutes();
            it.intensity = se.getIntensity().name();
            it.caloriesBurned = se.getCaloriesBurned();
            items.add(it);
        }
        r.items = items;
        return r;
    }
}
