package com.clinica.medical_system.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PrescripcionService {

    // In-memory storage: pacienteId -> list of prescriptions
    private final Map<String, List<Map<String, Object>>> prescripciones = new HashMap<>();

    public Map<String, Object> generarPrescripcion(String pacienteId, List<Map<String, String>> medicamentos) {
        String id = "PRE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String fecha = java.time.LocalDate.now().toString();

        Map<String, Object> prescripcion = new HashMap<>();
        prescripcion.put("id", id);
        prescripcion.put("pacienteId", pacienteId);
        prescripcion.put("fecha", fecha);
        prescripcion.put("medicamentos", medicamentos);

        prescripciones.computeIfAbsent(pacienteId, k -> new ArrayList<>()).add(prescripcion);
        return prescripcion;
    }

    public List<Map<String, Object>> obtenerPrescripcionesPorPaciente(String pacienteId) {
        return prescripciones.getOrDefault(pacienteId, Collections.emptyList());
    }
}
