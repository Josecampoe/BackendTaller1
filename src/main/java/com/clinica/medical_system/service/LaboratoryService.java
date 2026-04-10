package com.clinica.medical_system.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LaboratoryService {

    // Simulated reference ranges for lab exams
    private static final Map<String, Map<String, Object>> REFERENCE_RANGES = Map.of(
            "hemogram", Map.of("min", 4.5, "max", 11.0, "unit", "10^3/uL", "description", "White blood cells"),
            "glycemia", Map.of("min", 70.0, "max", 100.0, "unit", "mg/dL", "description", "Fasting blood glucose"),
            "lipid_profile", Map.of("min", 0.0, "max", 200.0, "unit", "mg/dL", "description", "Total cholesterol")
    );

    // In-memory storage: patientId -> list of results
    private final Map<String, List<Map<String, Object>>> results = new HashMap<>();

    public List<Map<String, Object>> requestExams(String patientId, List<String> exams) {
        List<Map<String, Object>> examResults = new ArrayList<>();

        for (String exam : exams) {
            String key = exam.toLowerCase().replace(" ", "_");
            Map<String, Object> ref = REFERENCE_RANGES.getOrDefault(key, Map.of(
                    "min", 0.0, "max", 100.0, "unit", "N/A", "description", exam
            ));

            double value = simulateValue((double) ref.get("min"), (double) ref.get("max"));
            boolean inRange = value >= (double) ref.get("min") && value <= (double) ref.get("max");

            Map<String, Object> result = new HashMap<>();
            result.put("exam", exam);
            result.put("value", value);
            result.put("unit", ref.get("unit"));
            result.put("referenceMin", ref.get("min"));
            result.put("referenceMax", ref.get("max"));
            result.put("inRange", inRange);
            result.put("description", ref.get("description"));
            result.put("date", java.time.LocalDate.now().toString());
            examResults.add(result);
        }

        results.computeIfAbsent(patientId, k -> new ArrayList<>()).addAll(examResults);
        return examResults;
    }

    public List<Map<String, Object>> getResultsByPatient(String patientId) {
        return results.getOrDefault(patientId, Collections.emptyList());
    }

    private double simulateValue(double min, double max) {
        // Simulate slightly outside range occasionally for realism
        double range = max - min;
        double simulated = min + (Math.random() * (range * 1.2));
        return Math.round(simulated * 100.0) / 100.0;
    }
}
