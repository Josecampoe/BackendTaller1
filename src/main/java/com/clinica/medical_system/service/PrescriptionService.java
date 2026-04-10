package com.clinica.medical_system.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrescriptionService {

    // In-memory storage: patientId -> list of prescriptions
    private final Map<String, List<Map<String, Object>>> prescriptions = new HashMap<>();

    public Map<String, Object> generatePrescription(String patientId, List<Map<String, String>> medications) {
        String id = "PRE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String date = java.time.LocalDate.now().toString();

        Map<String, Object> prescription = new HashMap<>();
        prescription.put("id", id);
        prescription.put("patientId", patientId);
        prescription.put("date", date);
        prescription.put("medications", medications);

        prescriptions.computeIfAbsent(patientId, k -> new ArrayList<>()).add(prescription);
        return prescription;
    }

    public List<Map<String, Object>> getPrescriptionsByPatient(String patientId) {
        return prescriptions.getOrDefault(patientId, Collections.emptyList());
    }
}
