package com.clinica.medical_system.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class LaboratorioService {

    // Simulated reference ranges for lab exams
    private static final Map<String, Map<String, Object>> REFERENCE_RANGES = Map.of(
            "hemogram", Map.of("min", 4.5, "max", 11.0, "unit", "10^3/uL", "description", "White blood cells"),
            "glycemia", Map.of("min", 70.0, "max", 100.0, "unit", "mg/dL", "description", "Fasting blood glucose"),
            "lipid_profile", Map.of("min", 0.0, "max", 200.0, "unit", "mg/dL", "description", "Total cholesterol")
    );

    // In-memory storage: pacienteId -> list of results
    private final Map<String, List<Map<String, Object>>> resultados = new HashMap<>();

    public List<Map<String, Object>> solicitarExamenes(String pacienteId, List<String> examenes) {
        List<Map<String, Object>> results = new ArrayList<>();

        for (String examen : examenes) {
            String key = examen.toLowerCase().replace(" ", "_");
            Map<String, Object> ref = REFERENCE_RANGES.getOrDefault(key, Map.of(
                    "min", 0.0, "max", 100.0, "unit", "N/A", "description", examen
            ));

            double value = simulateValue((double) ref.get("min"), (double) ref.get("max"));
            boolean inRange = value >= (double) ref.get("min") && value <= (double) ref.get("max");

            Map<String, Object> result = new HashMap<>();
            result.put("exam", examen);
            result.put("value", value);
            result.put("unit", ref.get("unit"));
            result.put("referenceMin", ref.get("min"));
            result.put("referenceMax", ref.get("max"));
            result.put("inRange", inRange);
            result.put("description", ref.get("description"));
            result.put("fecha", java.time.LocalDate.now().toString());
            results.add(result);
        }

        resultados.computeIfAbsent(pacienteId, k -> new ArrayList<>()).addAll(results);
        return results;
    }

    public List<Map<String, Object>> obtenerResultadosPorPaciente(String pacienteId) {
        return resultados.getOrDefault(pacienteId, Collections.emptyList());
    }

    private double simulateValue(double min, double max) {
        // Simulate slightly outside range occasionally for realism
        double range = max - min;
        double simulated = min + (Math.random() * (range * 1.2));
        return Math.round(simulated * 100.0) / 100.0;
    }
}
