package com.clinica.medical_system.controller;

import com.clinica.medical_system.dto.*;
import com.clinica.medical_system.facade.ClinicaFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clinica")
public class ClinicaController {

    private final ClinicaFacade facade;

    public ClinicaController(ClinicaFacade facade) {
        this.facade = facade;
    }

    // POST /api/clinica/paciente
    @PostMapping("/paciente")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRequest request) {
        return ResponseEntity.ok(
                facade.registerPatient(request.getName(), request.getDocument(), request.getEmail())
        );
    }

    // POST /api/clinica/cita
    @PostMapping("/cita")
    public ResponseEntity<?> scheduleAppointment(@RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(
                facade.scheduleAppointment(request.getPatientId(), request.getSpecialty(), request.getDate())
        );
    }

    // GET /api/clinica/historia/{pacienteId}
    @GetMapping("/historia/{pacienteId}")
    public ResponseEntity<Map<String, Object>> getHistory(@PathVariable String pacienteId) {
        return ResponseEntity.ok(facade.getCompleteHistory(pacienteId));
    }

    // POST /api/clinica/prescripcion
    @PostMapping("/prescripcion")
    public ResponseEntity<?> generatePrescription(@RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(
                facade.generatePrescription(request.getPatientId(), request.getMedications(),
                        request.getDose(), request.getDuration())
        );
    }

    // POST /api/clinica/laboratorio
    @PostMapping("/laboratorio")
    public ResponseEntity<?> requestExams(@RequestBody LabRequest request) {
        return ResponseEntity.ok(
                facade.requestExams(request.getPatientId(), request.getExams())
        );
    }

    // GET /api/clinica/medicos?especialidad=
    @GetMapping("/medicos")
    public ResponseEntity<List<String>> getDoctors(@RequestParam String especialidad) {
        return ResponseEntity.ok(facade.getDoctorsBySpecialty(especialidad));
    }

    // Manejo global de errores
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleError(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}