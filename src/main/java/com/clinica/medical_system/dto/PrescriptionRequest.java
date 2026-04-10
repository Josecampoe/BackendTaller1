package com.clinica.medical_system.dto;

import java.util.List;

public class PrescriptionRequest {
    private String patientId;
    private List<String> medications;
    private String dose;
    private String duration;

    public String getPatientId() { return patientId; }
    public List<String> getMedications() { return medications; }
    public String getDose() { return dose; }
    public String getDuration() { return duration; }
}