package com.clinica.medical_system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.clinica.medical_system.model.Patient;

@Service
public class PatientService {

    private final Map<String, Patient> patients = new HashMap<>();
    private final Map<String, String> credentials = new HashMap<>(); // documentNumber -> password

    public Patient registerPatient(String firstName, String lastName, String documentNumber, 
                                   String email, String phone, String birthDate, 
                                   String bloodType, String password, List<String> allergies) {
        validateUniqueDocument(documentNumber);
        String id = "PAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Patient patient = new Patient(id, firstName, lastName, documentNumber, email);
        patient.setPhone(phone);
        patient.setBirthDate(birthDate);
        patient.setBloodType(bloodType);
        patient.setPassword(password);
        if (allergies != null) {
            patient.setAllergies(allergies);
        }
        patients.put(id, patient);
        credentials.put(documentNumber, password);
        return patient;
    }

    public void validateUniqueDocument(String documentNumber) {
        boolean exists = patients.values().stream()
                .anyMatch(p -> p.getDocumentNumber().equals(documentNumber));
        if (exists) {
            throw new IllegalArgumentException("A patient with document " + documentNumber + " already exists");
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

    public Map<String, String> authenticatePatient(String documentNumber, String password) {
        String storedPassword = credentials.get(documentNumber);
        if (storedPassword == null || !storedPassword.equals(password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        
        Patient patient = patients.values().stream()
                .filter(p -> p.getDocumentNumber().equals(documentNumber))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Patient not found"));
        
        // Simple token generation (in production, use JWT)
        String token = "TOKEN-" + UUID.randomUUID().toString();
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("patientId", patient.getId());
        response.put("patientName", patient.getFullName());
        return response;
    }
}
