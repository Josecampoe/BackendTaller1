package com.clinica.medical_system.controller;

import com.clinica.medical_system.dto.*;
import com.clinica.medical_system.facade.ClinicFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clinic")
public class ClinicController {

    private final ClinicFacade facade;

    public ClinicController(ClinicFacade facade) {
        this.facade = facade;
    }

    // POST /api/clinic/patient
    @PostMapping("/patient")
    public ResponseEntity<?> registerPatient(@RequestBody PatientRequest request) {
        return ResponseEntity.ok(
                facade.registerPatient(request.getName(), request.getDocument(), request.getEmail())
        );
    }

    // POST /api/clinic/appointment
    @PostMapping("/appointment")
    public ResponseEntity<?> scheduleAppointment(@RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(
                facade.scheduleAppointment(request.getPatientId(), request.getSpecialty(), request.getDate())
        );
    }

    // GET /api/clinic/history/{patientId}
    @GetMapping("/history/{patientId}")
    public ResponseEntity<Map<String, Object>> getHistory(@PathVariable String patientId) {
        return ResponseEntity.ok(facade.getCompleteHistory(patientId));
    }

    // POST /api/clinic/prescription
    @PostMapping("/prescription")
    public ResponseEntity<?> generatePrescription(@RequestBody PrescriptionRequest request) {
        return ResponseEntity.ok(
                facade.generatePrescription(request.getPatientId(), request.getMedications(),
                        request.getDose(), request.getDuration())
        );
    }

    // POST /api/clinic/laboratory
    @PostMapping("/laboratory")
    public ResponseEntity<?> requestExams(@RequestBody LabRequest request) {
        return ResponseEntity.ok(
                facade.requestExams(request.getPatientId(), request.getExams())
        );
    }

    // GET /api/clinic/doctors?specialty=
    @GetMapping("/doctors")
    public ResponseEntity<List<String>> getDoctors(@RequestParam String specialty) {
        return ResponseEntity.ok(facade.getDoctorsBySpecialty(specialty));
    }

    // Global error handler
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleError(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}