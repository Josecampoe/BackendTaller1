package com.clinica.medical_system.model;

public class Consultation {

    private String id;
    private String patientId;
    private String diagnosis;
    private String date;
    private String doctor;

    public Consultation() {}

    public Consultation(String id, String patientId, String diagnosis, String date, String doctor) {
        this.id = id;
        this.patientId = patientId;
        this.diagnosis = diagnosis;
        this.date = date;
        this.doctor = doctor;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDoctor() { return doctor; }
    public void setDoctor(String doctor) { this.doctor = doctor; }
}
