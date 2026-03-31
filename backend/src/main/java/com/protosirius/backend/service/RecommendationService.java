package com.protosirius.backend.service;

import com.protosirius.backend.dto.RecommendationRequest;
import com.protosirius.backend.entity.Athlete;
import com.protosirius.backend.entity.Exercise;
import com.protosirius.backend.entity.SessionExercise;
import com.protosirius.backend.entity.TrainingSession;


import com.protosirius.backend.repository.AthleteRep;
import com.protosirius.backend.repository.ExerciseRep;
import com.protosirius.backend.repository.SessionExerciseRep;
import com.protosirius.backend.repository.TrainingSessionRep;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {

    private final AthleteRep athleteRepository;

    private final TrainingSessionRep trainingSessionRepository;


    private final SessionExerciseRep sessionExerciseRepository;


    private final ExerciseRep exerciseRepository;

    public RecommendationService(AthleteRep athleteRepository, TrainingSessionRep trainingSessionRepository,

                                 SessionExerciseRep sessionExerciseRepository,

                                 ExerciseRep exerciseRepository) {


        this.athleteRepository =athleteRepository;

        this.trainingSessionRepository =   trainingSessionRepository;


        this.sessionExerciseRepository = sessionExerciseRepository;
        this.exerciseRepository = exerciseRepository;
    }


    // my algo : recommander la prochaine seance


    public Map<String, Object> recommendNext(Long athleteId) {      //
        Athlete athlete = athleteRepository.findById(athleteId).orElseThrow();


        List<TrainingSession> last = trainingSessionRepository.findTop3ByAthleteIdOrderBySessionDateDesc(athleteId);  

        // 3 seance

        Map<String, Integer> counts = new HashMap<>(); 


        String lastIntensity = null;


        for ( int i = 0;i < last.size();  i++ ) {

            TrainingSession s = last.get(i);


            if (i == 0) lastIntensity =pickSessionIntensity(s);


            List<SessionExercise> ses = sessionExerciseRepository.findBySessionId(s.getId());


            for ( SessionExercise se : ses  ) {
                String mg = se.getExercise().getMuscleGroup().getName(); // nom de muscle group


                counts.put( mg, counts.getOrDefault(mg, 0 ) + 1);
            }
        }

        String target = pickLeast(counts);  // mon methode pickleast


        List<Exercise > candidates = exerciseRepository.findByMuscleGroupNameIgnoreCase(target);
        Collections.shuffle(candidates);

        // prendre les 3 1er exo


        List< Exercise > picked = candidates.size() > 3 ? candidates.subList(0, 3) : candidates;

        String intensity = "MEDIUM";

        if ( lastIntensity == null ) intensity = "MEDIUM";

        else if ("HIGH".equalsIgnoreCase(lastIntensity  ) ) intensity = "MEDIUM";
        else intensity = "HIGH";

        int duration = athlete.getAvailableMinutes() == null ? 30 : athlete.getAvailableMinutes();

        List<  Map<String, Object>> ex=new ArrayList<>();


        for (Exercise e : picked) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", e.getId());

            m.put("name", e.getName());
            m.put("muscleGroup", e.getMuscleGroup().getName());
            m.put("difficulty", e.getDifficulty());


            ex.add(m);
        }

        List<String> reasons = new ArrayList<>();   // le reason du choix


        reasons.add("goal=" + safe(athlete.getGoal()) );
        reasons.add("targetMuscle=" + target   );
        reasons.add("intensity=" + intensity  );

        Map<String, Object> res = new HashMap<>();
        res.put("athleteId", athlete.getId());

        res.put("athleteName", athlete.getFullName());
        res.put("goal", athlete.getGoal());
        
        res.put("level", athlete.getLevel());
        res.put("weightKg", athlete.getWeightKg());
        res.put("availableMinutes", duration);
        res.put("focusMuscleGr", target);

        res.put("focusMuscleGroup", target);
        
        res.put("intensity", intensity);
        res.put("exercises", ex);
        
        res.put("reasons", reasons);
        return res;
    }

    private String pickLeast(Map<String, Integer> counts) {   //prendre le min
        if ( counts.isEmpty() ) return "FULL_BODY";

        String best = null;
        
        int v = Integer.MAX_VALUE;
        
        for ( Map.Entry<String, Integer> e : counts.entrySet() ) {

            if ( e.getValue() < v ) {
                v = e.getValue();
                best = e.getKey();
            }
        }
        return best == null ? "FULL_BODY" : best;
    }

    private String pickSessionIntensity(TrainingSession s) {    // choisir intensite de seance 


        if (s.getSessionIntensity() !=null) return s.getSessionIntensity(); 

        
        List<SessionExercise> ses = sessionExerciseRepository.findBySessionId(s.getId());
        if (ses.isEmpty()) return null;
        return ses.get(0).getIntensity();
    }

    private String safe(String s) {
        return s == null ? "UNKNOWN" : s;
    }


    public Map<String, Object> recommendWithFilters(RecommendationRequest request) {
    Athlete athlete = athleteRepository.findById(request.getAthleteId()).orElseThrow();

    String goal = request.getGoal();
    if (goal == null || goal.isBlank()) {
        goal = athlete.getGoal();
    }

    String level = request.getLevel();
    if (level == null || level.isBlank()) {
        level = athlete.getLevel();
    }

    String equipment = request.getEquipment();
    if (equipment == null || equipment.isBlank()) {
        equipment = athlete.getEquipment();
    }

    Integer availableMinutes = request.getAvailableMinutes();
    if (availableMinutes == null) {
        availableMinutes = athlete.getAvailableMinutes();
    }
    if (availableMinutes == null) {
        availableMinutes = 30;
    }

    List<TrainingSession> last = trainingSessionRepository.findTop3ByAthleteIdOrderBySessionDateDesc(athlete.getId());

    Map<String, Integer> counts = new HashMap<>();
    String lastIntensity = null;

    for (int i = 0; i < last.size(); i++) {
        TrainingSession s = last.get(i);

        if (i == 0) {
            lastIntensity = pickSessionIntensity(s);
        }

        List<SessionExercise> ses = sessionExerciseRepository.findBySessionId(s.getId());

        for (SessionExercise se : ses) {
            if (se.getExercise() != null
                    && se.getExercise().getMuscleGroup() != null
                    && se.getExercise().getMuscleGroup().getName() != null) {
                String mg = se.getExercise().getMuscleGroup().getName();
                counts.put(mg, counts.getOrDefault(mg, 0) + 1);
            }
        }
    }

    String target = pickLeast(counts);

    List<Exercise> candidates = exerciseRepository.findByMuscleGroupNameIgnoreCase(target);
    if (candidates.isEmpty()) {
        candidates = exerciseRepository.findAll();
    }

    List<Map<String, Object>> scoredExercises = new ArrayList<>();

    for (Exercise e : candidates) {
        int score = 0;

        if (e.getMuscleGroup() != null
                && e.getMuscleGroup().getName() != null
                && e.getMuscleGroup().getName().equalsIgnoreCase(target)) {
            score = score + 5;
        }

        if (matchesGoal(e, goal)) {
            score = score + 4;
        }

        if (matchesLevel(e, level)) {
            score = score + 3;
        } else {
            score = score - 2;
        }

        if (matchesEquipment(e, equipment)) {
            score = score + 3;
        } else {
            score = score - 2;
        }

        Map<String, Object> row = new HashMap<>();
        row.put("id", e.getId());
        row.put("name", e.getName());
        row.put("muscleGroup", e.getMuscleGroup() != null ? e.getMuscleGroup().getName() : "");
        row.put("difficulty", e.getDifficulty());
        row.put("score", score);

        scoredExercises.add(row);
    }

    scoredExercises.sort((a, b) -> Integer.compare((Integer) b.get("score"), (Integer) a.get("score")));

    int limit = pickExerciseLimit(availableMinutes);
    if (scoredExercises.size() > limit) {
        scoredExercises = new ArrayList<>(scoredExercises.subList(0, limit));
    }

    String intensity = "MEDIUM";

    if (lastIntensity == null) intensity = "MEDIUM";
    else if ("HIGH".equalsIgnoreCase(lastIntensity)) intensity = "MEDIUM";
    else intensity = "HIGH";

    List<String> reasons = new ArrayList<>();
    reasons.add("goal=" + safe(goal));
    reasons.add("targetMuscle=" + target);
    reasons.add("level=" + safe(level));
    reasons.add("equipment=" + safe(equipment));
    reasons.add("intensity=" + intensity);

    Map<String, Object> res = new HashMap<>();
    res.put("athleteId", athlete.getId());
    res.put("athleteName", athlete.getFullName());
    res.put("goal", goal);
    res.put("level", level);
    res.put("weightKg", athlete.getWeightKg());
    res.put("availableMinutes", availableMinutes);
    res.put("focusMuscleGr", target);
    res.put("focusMuscleGroup", target);
    res.put("intensity", intensity);
    res.put("exercises", scoredExercises);
    res.put("reasons", reasons);
    return res;
}
private int pickExerciseLimit(Integer availableMinutes) {
    if (availableMinutes == null) {
        return 3;
    }

    if (availableMinutes <= 20) {
        return 2;
    }

    if (availableMinutes <= 45) {
        return 3;
    }

    return 4;
}

private boolean matchesGoal(Exercise exercise, String goal) {
    if (goal == null || goal.isBlank()) {
        return true;
    }

    String g = goal.toLowerCase();
    String type = exercise.getType() == null ? "" : exercise.getType().toLowerCase();
    String difficulty = exercise.getDifficulty() == null ? "" : exercise.getDifficulty().toLowerCase();

    if (g.contains("prise") || g.contains("mass") || g.contains("muscle")) {
        return type.contains("strength") || type.contains("resistance") || difficulty.contains("hard") || difficulty.contains("medium");
    }

    if (g.contains("perte") || g.contains("loss") || g.contains("cardio")) {
        return type.contains("cardio") || type.contains("hiit") || difficulty.contains("easy") || difficulty.contains("medium");
    }

    if (g.contains("endurance")) {
        return type.contains("cardio") || difficulty.contains("easy") || difficulty.contains("medium");
    }

    return true;
}

private boolean matchesLevel(Exercise exercise, String level) {
    if (level == null || level.isBlank()) {
        return true;
    }

    String l = level.toLowerCase();
    String d = exercise.getDifficulty() == null ? "" : exercise.getDifficulty().toLowerCase();

    if (l.contains("beginner") || l.contains("debutant")) {
        return d.contains("easy") || d.contains("medium") || d.isBlank();
    }

    if (l.contains("intermediate")) {
        return d.contains("medium") || d.contains("easy") || d.isBlank();
    }

    if (l.contains("advanced") || l.contains("avance")) {
        return true;
    }

    return true;
}

private boolean matchesEquipment(Exercise exercise, String equipment) {
    if (equipment == null || equipment.isBlank()) {
        return true;
    }

    String userEquipment = equipment.toLowerCase();
    String exerciseEquipment = exercise.getEquipment() == null ? "" : exercise.getEquipment().toLowerCase();

    if (exerciseEquipment.isBlank()) {
        return true;
    }

    return exerciseEquipment.contains(userEquipment);
}
}
