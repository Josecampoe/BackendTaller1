package com.clinica.medical_system.facade;

import com.clinica.medical_system.model.*;
import com.clinica.medical_system.service.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ClinicFacade {

    private final PatientService patientService;
    private final AgendaService agendaService;
    private final MedicalHistoryService historyService;
    private final PrescriptionService prescriptionService;
    private final LaboratoryService laboratoryService;

    public ClinicFacade(PatientService patientService,
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

    // Registers a new patient
    public Patient registerPatient(String name, String document, String email) {
        return patientService.register(name, document, email);
    }

    // Schedules an appointment and logs it in medical history
    public Appointment scheduleAppointment(String patientId, String specialty, String date) {
        patientService.findById(patientId);
        Appointment appointment = agendaService.schedule(patientId, specialty, date);
        historyService.addEntry(patientId, "Appointment scheduled: " + specialty + " on " + date);
        return appointment;
    }

    // Returns appointments + prescriptions + lab results in one object
    public Map<String, Object> getCompleteHistory(String patientId) {
        patientService.findById(patientId);
        Map<String, Object> history = new LinkedHashMap<>();
        history.put("appointments",  agendaService.getByPatient(patientId));
        history.put("prescriptions", prescriptionService.getByPatient(patientId));
        history.put("labResults",    laboratoryService.getByPatient(patientId));
        history.put("notes",         historyService.getHistory(patientId));
        return history;
    }

    // Generates a prescription after validating patient allergies
    public Prescription generatePrescription(String patientId, List<String> medications, String dose, String duration) {
        List<String> allergies = patientService.getAllergies(patientId);
        medications.forEach(med -> {
            if (allergies.contains(med))
                throw new RuntimeException("Allergy alert: patient is allergic to " + med);
        });
        historyService.addEntry(patientId, "Prescription generated: " + medications);
        return prescriptionService.generate(patientId, medications, dose, duration);
    }

    // Requests lab exams and logs them in medical history
    public List<LabResult> requestExams(String patientId, List<String> exams) {
        patientService.findById(patientId);
        historyService.addEntry(patientId, "Lab exams requested: " + exams);
        return laboratoryService.requestExams(patientId, exams);
    }

    // Returns available doctors by specialty
    public List<String> getDoctorsBySpecialty(String specialty) {
        return agendaService.getDoctorsBySpecialty(specialty);
    }
}