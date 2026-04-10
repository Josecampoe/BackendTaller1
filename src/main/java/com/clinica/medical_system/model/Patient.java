package com.clinica.medical_system.model;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String id;
    private String name;
    private String document;
    private String email;
    private List<String> allergies = new ArrayList<>();

    public Patient(String id, String name, String document, String email) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDocument() { return document; }
    public String getEmail() { return email; }
    public List<String> getAllergies() { return allergies; }
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }
}