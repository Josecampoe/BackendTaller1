package com.clinica.medical_system.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.medical_system.facade.ClinicaFacade;
import com.clinica.medical_system.model.Cita;
import com.clinica.medical_system.model.Medico;
import com.clinica.medical_system.model.Paciente;
import com.clinica.medical_system.service.AgendaService;
import com.clinica.medical_system.service.PacienteService;

@RestController
@RequestMapping("/api/clinica")
@CrossOrigin(origins = "*")
public class ClinicaController {

    private final ClinicaFacade clinicaFacade;
    private final PacienteService pacienteService;
    private final AgendaService agendaService;

    public ClinicaController(ClinicaFacade clinicaFacade,
                             PacienteService pacienteService,
                             AgendaService agendaService) {
        this.clinicaFacade = clinicaFacade;
        this.pacienteService = pacienteService;
        this.agendaService = agendaService;
    }

    // POST /api/clinica/paciente
    @PostMapping("/paciente")
    public ResponseEntity<?> registrarPaciente(@RequestBody Map<String, Object> body) {
        try {
            String nombre = (String) body.get("nombre");
            String documento = (String) body.get("documento");
            String email = (String) body.get("email");
            List<String> alergias = (List<String>) body.getOrDefault("alergias", List.of());
            Paciente paciente = pacienteService.registrarPaciente(nombre, documento, email, alergias);
            return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // POST /api/clinica/cita
    @PostMapping("/cita")
    public ResponseEntity<?> agendarCita(@RequestBody Map<String, String> body) {
        try {
            Cita cita = clinicaFacade.agendarCita(
                    body.get("pacienteId"),
                    body.get("medicoId"),
                    body.get("fecha")
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(cita);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/clinica/historia/{pacienteId}
    @GetMapping("/historia/{pacienteId}")
    public ResponseEntity<?> verHistoriaCompleta(@PathVariable String pacienteId) {
        try {
            return ResponseEntity.ok(clinicaFacade.verHistoriaCompleta(pacienteId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // POST /api/clinica/prescripcion
    @PostMapping("/prescripcion")
    public ResponseEntity<?> generarPrescripcion(@RequestBody Map<String, Object> body) {
        try {
            String pacienteId = (String) body.get("pacienteId");
            List<Map<String, String>> medicamentos = (List<Map<String, String>>) body.get("medicamentos");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clinicaFacade.generarPrescripcion(pacienteId, medicamentos));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // POST /api/clinica/laboratorio
    @PostMapping("/laboratorio")
    public ResponseEntity<?> solicitarExamenes(@RequestBody Map<String, Object> body) {
        try {
            String pacienteId = (String) body.get("pacienteId");
            List<String> examenes = (List<String>) body.get("examenes");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clinicaFacade.solicitarExamenes(pacienteId, examenes));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    // GET /api/clinica/medicos?especialidad=
    @GetMapping("/medicos")
    public ResponseEntity<List<Medico>> obtenerMedicos(
            @RequestParam(required = false) String especialidad) {
        if (especialidad != null && !especialidad.isBlank()) {
            return ResponseEntity.ok(agendaService.obtenerMedicosPorEspecialidad(especialidad));
        }
        return ResponseEntity.ok(agendaService.obtenerTodosMedicos());
    }
}
