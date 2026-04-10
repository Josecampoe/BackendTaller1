package com.clinica.medical_system.dto;

public class AppointmentRequest {
    private String patientId;
    private String specialty;
    private String date;

    public String getPatientId() { return patientId; }
    public String getSpecialty() { return specialty; }
    public String getDate() { return date; }
}