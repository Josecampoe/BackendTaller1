package com.clinica.medical_system.model;

import java.util.ArrayList;
import java.util.List;

public class Patient {

    private String id;
    private String name;
    private String documentId;
    private String email;
    private List<String> allergies;

    public Patient() {
        this.allergies = new ArrayList<>();
    }

    public Patient(String id, String name, String documentId, String email) {
        this.id = id;
        this.name = name;
        this.documentId = documentId;
        this.email = email;
        this.allergies = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDocumentId() { return documentId; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getAllergies() { return allergies; }
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }
}
