package com.clinica.medical_system.model;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    private String id;
    private String name;
    private String specialty;
    private List<String> availableSlots;

    public Doctor() {
        this.availableSlots = new ArrayList<>();
    }

    public Doctor(String id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.availableSlots = generateDefaultSlots();
    }

    private List<String> generateDefaultSlots() {
        // Generate some default time slots
        return List.of(
            "2026-05-20T09:00",
            "2026-05-20T10:00",
            "2026-05-20T11:00",
            "2026-05-20T14:00",
            "2026-05-20T15:00",
            "2026-05-21T09:00",
            "2026-05-21T10:00"
        );
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public List<String> getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(List<String> availableSlots) { this.availableSlots = availableSlots; }
}
