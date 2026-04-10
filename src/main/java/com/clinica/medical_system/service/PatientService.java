package com.clinica.medical_system.service;

import com.clinica.medical_system.model.Patient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {

    private final Map<String, Patient> patients = new HashMap<>();

    public Patient registerPatient(String name, String documentId, String email, List<String> allergies) {
        validateUniqueDocument(documentId);
        String id = "PAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Patient patient = new Patient(id, name, documentId, email);
        if (allergies != null) {
            patient.setAllergies(allergies);
        }
        patients.put(id, patient);
        return patient;
    }

    public void validateUniqueDocument(String documentId) {
        boolean exists = patients.values().stream()
                .anyMatch(p -> p.getDocumentId().equals(documentId));
        if (exists) {
            throw new IllegalArgumentException("A patient with document " + documentId + " already exists");
        }
    }

    public Patient getPatientProfile(String patientId) {
        Patient patient = patients.get(patientId);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found: " + patientId);
        }
        return patient;
    }

    public boolean patientExists(String patientId) {
        return patients.containsKey(patientId);
    }
}
