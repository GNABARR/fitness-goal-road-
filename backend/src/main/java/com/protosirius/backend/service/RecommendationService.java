package com.protosirius.backend.service;

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
}
