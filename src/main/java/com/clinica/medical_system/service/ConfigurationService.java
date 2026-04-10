package com.clinica.medical_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    public List<String> getSpecialties() {
        return List.of(
            "General Medicine",
            "Cardiology",
            "Pediatrics",
            "Dermatology",
            "Neurology"
        );
    }

    public List<String> getBloodTypes() {
        return List.of(
            "A+", "A-",
            "B+", "B-",
            "AB+", "AB-",
            "O+", "O-"
        );
    }

    public List<String> getAvailableExams() {
        return List.of(
            "complete_blood_count",
            "blood_glucose",
            "lipid_profile"
        );
    }
}
