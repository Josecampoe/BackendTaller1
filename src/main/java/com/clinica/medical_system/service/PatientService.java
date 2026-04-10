package com.clinica.medical_system.service;

import com.clinica.medical_system.model.Patient;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PatientService {

    // Almacenamiento en memoria
    private final Map<String, Patient> patients = new HashMap<>();

    public Patient register(String name, String document, String email) {
        // Validar documento único
        boolean exists = patients.values().stream()
                .anyMatch(p -> p.getDocument().equals(document));
        if (exists) throw new RuntimeException("Document already registered");

        String id = UUID.randomUUID().toString();
        Patient patient = new Patient(id, name, document, email);
        patients.put(id, patient);
        return patient;
    }

    public Patient findById(String id) {
        Patient patient = patients.get(id);
        if (patient == null) throw new RuntimeException("Patient not found");
        return patient;
    }

    public List<String> getAllergies(String patientId) {
        return findById(patientId).getAllergies();
    }
}