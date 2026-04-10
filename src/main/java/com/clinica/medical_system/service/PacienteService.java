package com.clinica.medical_system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.clinica.medical_system.model.Paciente;

@Service
public class PacienteService {

    private final Map<String, Paciente> pacientes = new HashMap<>();

    public Paciente registrarPaciente(String nombre, String documento, String email, List<String> alergias) {
        validarDocumentoUnico(documento);
        String id = "PAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Paciente paciente = new Paciente(id, nombre, documento, email);
        if (alergias != null) {
            paciente.setAlergias(alergias);
        }
        pacientes.put(id, paciente);
        return paciente;
    }

    public void validarDocumentoUnico(String documento) {
        boolean exists = pacientes.values().stream()
                .anyMatch(p -> p.getDocumento().equals(documento));
        if (exists) {
            throw new IllegalArgumentException("A patient with document " + documento + " already exists");
        }
    }

    public Paciente consultarPerfil(String pacienteId) {
        Paciente paciente = pacientes.get(pacienteId);
        if (paciente == null) {
            throw new NoSuchElementException("Patient not found: " + pacienteId);
        }
        return paciente;
    }

    public boolean existePaciente(String pacienteId) {
        return pacientes.containsKey(pacienteId);
    }
}
