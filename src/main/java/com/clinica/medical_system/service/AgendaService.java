package com.clinica.medical_system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.clinica.medical_system.model.Cita;
import com.clinica.medical_system.model.Medico;

@Service
public class AgendaService {

    // 5 predefined doctors with 3 specialties
    private final List<Medico> medicos = List.of(
            new Medico("MED-001", "Dr. Carlos Ramirez", "Cardiology"),
            new Medico("MED-002", "Dr. Laura Gomez", "Cardiology"),
            new Medico("MED-003", "Dr. Andres Torres", "Pediatrics"),
            new Medico("MED-004", "Dr. Sofia Herrera", "Pediatrics"),
            new Medico("MED-005", "Dr. Miguel Ruiz", "General Medicine")
    );

    private final Map<String, Cita> citas = new HashMap<>();

    public List<Medico> obtenerMedicosPorEspecialidad(String especialidad) {
        return medicos.stream()
                .filter(m -> m.getEspecialidad().equalsIgnoreCase(especialidad))
                .collect(Collectors.toList());
    }

    public List<Medico> obtenerTodosMedicos() {
        return medicos;
    }

    public Cita agendarCita(String pacienteId, String medicoId, String fecha) {
        Medico medico = medicos.stream()
                .filter(m -> m.getId().equals(medicoId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Doctor not found: " + medicoId));

        String citaId = "CIT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Cita cita = new Cita(citaId, pacienteId, medicoId, medico.getNombre(),
                medico.getEspecialidad(), fecha);
        citas.put(citaId, cita);
        return cita;
    }

    public Cita cancelarCita(String citaId) {
        Cita cita = citas.get(citaId);
        if (cita == null) {
            throw new NoSuchElementException("Appointment not found: " + citaId);
        }
        cita.setEstado("CANCELLED");
        return cita;
    }

    public List<Cita> obtenerCitasPorPaciente(String pacienteId) {
        return citas.values().stream()
                .filter(c -> c.getPacienteId().equals(pacienteId))
                .collect(Collectors.toList());
    }
}
