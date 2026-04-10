package com.clinica.medical_system.facade;

import com.clinica.medical_system.model.*;
import com.clinica.medical_system.service.*;
import org.springframework.stereotype.Component;

import java.util.*;

// Fachada principal — orquesta todos los subsistemas
@Component
public class ClinicaFacade {

    private final PatientService patientService;
    private final AgendaService agendaService;
    private final MedicalHistoryService historyService;
    private final PrescriptionService prescriptionService;
    private final LaboratoryService laboratoryService;

    public ClinicaFacade(PatientService patientService,
                         AgendaService agendaService,
                         MedicalHistoryService historyService,
                         PrescriptionService prescriptionService,
                         LaboratoryService laboratoryService) {
        this.patientService      = patientService;
        this.agendaService       = agendaService;
        this.historyService      = historyService;
        this.prescriptionService = prescriptionService;
        this.laboratoryService   = laboratoryService;
    }

    // Registra un paciente nuevo
    public Patient registerPatient(String name, String document, String email) {
        return patientService.register(name, document, email);
    }

    // Agenda una cita y registra el evento en la historia clínica
    public Appointment scheduleAppointment(String patientId, String specialty, String date) {
        patientService.findById(patientId); // valida que existe
        Appointment appointment = agendaService.schedule(patientId, specialty, date);
        historyService.addEntry(patientId, "Appointment scheduled: " + specialty + " on " + date);
        return appointment;
    }

    // Consolida citas + prescripciones + laboratorios en un solo objeto
    public Map<String, Object> getCompleteHistory(String patientId) {
        patientService.findById(patientId); // valida que existe
        Map<String, Object> history = new LinkedHashMap<>();
        history.put("appointments",   agendaService.getByPatient(patientId));
        history.put("prescriptions",  prescriptionService.getByPatient(patientId));
        history.put("labResults",     laboratoryService.getByPatient(patientId));
        history.put("notes",          historyService.getHistory(patientId));
        return history;
    }

    // Genera prescripción validando alergias del paciente
    public Prescription generatePrescription(String patientId, List<String> medications, String dose, String duration) {
        List<String> allergies = patientService.getAllergies(patientId);
        // Validación de alergias
        medications.forEach(med -> {
            if (allergies.contains(med))
                throw new RuntimeException("Allergy alert: patient is allergic to " + med);
        });
        historyService.addEntry(patientId, "Prescription generated: " + medications);
        return prescriptionService.generate(patientId, medications, dose, duration);
    }

    // Solicita exámenes y registra en historia clínica
    public List<LabResult> requestExams(String patientId, List<String> exams) {
        patientService.findById(patientId); // valida que existe
        historyService.addEntry(patientId, "Lab exams requested: " + exams);
        return laboratoryService.requestExams(patientId, exams);
    }

    // Retorna médicos por especialidad
    public List<String> getDoctorsBySpecialty(String specialty) {
        return agendaService.getDoctorsBySpecialty(specialty);
    }
}