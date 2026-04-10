package com.clinica.medical_system.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MedicalHistoryService {

    // patientId -> lista de entradas de historial
    private final Map<String, List<String>> history = new HashMap<>();

    public void addEntry(String patientId, String entry) {
        history.computeIfAbsent(patientId, k -> new ArrayList<>()).add(entry);
    }

    public List<String> getHistory(String patientId) {
        return history.getOrDefault(patientId, List.of());
    }
}