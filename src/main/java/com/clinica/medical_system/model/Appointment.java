package com.clinica.medical_system.model;

public class Appointment {
    private String id;
    private String patientId;
    private String doctorName;
    private String specialty;
    private String date;
    private String status;

    public Appointment(String id, String patientId, String doctorName, String specialty, String date) {
        this.id = id;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.specialty = specialty;
        this.date = date;
        this.status = "SCHEDULED";
    }

    public String getId() { return id; }
    public String getPatientId() { return patientId; }
    public String getDoctorName() { return doctorName; }
    public String getSpecialty() { return specialty; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}