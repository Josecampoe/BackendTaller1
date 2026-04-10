package com.clinica.medical_system.service;

import com.clinica.medical_system.model.Consultation;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalHistoryService {

    private final Map<String, Consultation> consultations = new HashMap<>();

    public Consultation registerConsultation(String patientId, String diagnosis, String doctor) {
        String id = "CON-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String date = java.time.LocalDate.now().toString();
        Consultation consultation = new Consultation(id, patientId, diagnosis, date, doctor);
        consultations.put(id, consultation);
        return consultation;
    }

    public List<Consultation> getPatientHistory(String patientId) {
        return consultations.values().stream()
                .filter(c -> c.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }

    public List<String> getPatientAllergies(String patientId, PatientService patientService) {
        return patientService.getPatientProfile(patientId).getAllergies();
    }
}
