package com.clinica.medical_system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.clinica.medical_system.model.Consulta;

@Service
public class HistoriaClinicaService {

    private final Map<String, Consulta> consultas = new HashMap<>();

    public Consulta registrarConsulta(String pacienteId, String diagnostico, String medico) {
        String id = "CON-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String fecha = java.time.LocalDate.now().toString();
        Consulta consulta = new Consulta(id, pacienteId, diagnostico, fecha, medico);
        consultas.put(id, consulta);
        return consulta;
    }

    public List<Consulta> consultarHistoria(String pacienteId) {
        return consultas.values().stream()
                .filter(c -> c.getPacienteId().equals(pacienteId))
                .collect(Collectors.toList());
    }

    public List<String> consultarAlergias(String pacienteId, PacienteService pacienteService) {
        return pacienteService.consultarPerfil(pacienteId).getAlergias();
    }
}
