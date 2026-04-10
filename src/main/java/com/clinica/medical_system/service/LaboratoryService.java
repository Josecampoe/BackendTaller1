package com.clinica.medical_system.service;

import com.clinica.medical_system.model.LabResult;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LaboratoryService {

    private final Map<String, List<LabResult>> results = new HashMap<>();

    // Resultados simulados por tipo de examen
    private LabResult simulate(String exam) {
        return switch (exam.toLowerCase()) {
            case "hemograma"      -> new LabResult("Hemograma",      13.5, 12.0, 17.5, "g/dL");
            case "glicemia"       -> new LabResult("Glicemia",       95.0, 70.0, 100.0, "mg/dL");
            case "perfil lipidico"-> new LabResult("Perfil Lipídico",210.0, 0.0, 200.0, "mg/dL");
            default               -> new LabResult(exam,             0.0,  0.0,  0.0,  "N/A");
        };
    }

    public List<LabResult> requestExams(String patientId, List<String> exams) {
        List<LabResult> labResults = exams.stream().map(this::simulate).toList();
        results.computeIfAbsent(patientId, k -> new ArrayList<>()).addAll(labResults);
        return labResults;
    }

    public List<LabResult> getByPatient(String patientId) {
        return results.getOrDefault(patientId, List.of());
    }
}