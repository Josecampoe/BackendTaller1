package com.clinica.medical_system.service;

import com.clinica.medical_system.model.Appointment;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AgendaService {

    private final Map<String, Appointment> appointments = new HashMap<>();

    // Médicos predefinidos por especialidad
    private final Map<String, List<String>> doctors = Map.of(
            "Cardiology",  List.of("Dr. Smith", "Dr. Johnson"),
            "Neurology",   List.of("Dr. Brown", "Dr. Davis"),
            "Pediatrics",  List.of("Dr. Wilson")
    );

    public List<String> getDoctorsBySpecialty(String specialty) {
        return doctors.getOrDefault(specialty, List.of());
    }

    public List<String> getSpecialties() {
        return new ArrayList<>(doctors.keySet());
    }

    public Appointment schedule(String patientId, String specialty, String date) {
        List<String> available = getDoctorsBySpecialty(specialty);
        if (available.isEmpty()) throw new RuntimeException("No doctors for specialty: " + specialty);

        String id = UUID.randomUUID().toString();
        // Asigna el primer médico disponible
        Appointment appointment = new Appointment(id, patientId, available.get(0), specialty, date);
        appointments.put(id, appointment);
        return appointment;
    }

    public List<Appointment> getByPatient(String patientId) {
        return appointments.values().stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .toList();
    }
}