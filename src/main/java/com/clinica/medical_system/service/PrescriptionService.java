package com.clinica.medical_system.service;

import com.clinica.medical_system.model.Prescription;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PrescriptionService {

    private final Map<String, List<Prescription>> prescriptions = new HashMap<>();

    public Prescription generate(String patientId, List<String> medications, String dose, String duration) {
        String id = UUID.randomUUID().toString();
        Prescription prescription = new Prescription(id, patientId, medications, dose, duration);
        prescriptions.computeIfAbsent(patientId, k -> new ArrayList<>()).add(prescription);
        return prescription;
    }

    public List<Prescription> getByPatient(String patientId) {
        return prescriptions.getOrDefault(patientId, List.of());
    }
}