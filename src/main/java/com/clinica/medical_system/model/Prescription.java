package com.clinica.medical_system.model;

import java.util.List;

public class Prescription {
    private String id;
    private String patientId;
    private List<String> medications;
    private String dose;
    private String duration;

    public Prescription(String id, String patientId, List<String> medications, String dose, String duration) {
        this.id = id;
        this.patientId = patientId;
        this.medications = medications;
        this.dose = dose;
        this.duration = duration;
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public List<String> getMedications() { return medications; }
    public String getDose() { return dose; }
    public String getDuration() { return duration; }
}