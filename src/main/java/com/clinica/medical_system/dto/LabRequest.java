package com.clinica.medical_system.dto;

import java.util.List;

public class LabRequest {
    private String patientId;
    private List<String> exams;

    public String getPatientId() { return patientId; }
    public List<String> getExams() { return exams; }
}