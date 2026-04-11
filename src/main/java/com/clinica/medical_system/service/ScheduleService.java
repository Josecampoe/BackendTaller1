package com.clinica.medical_system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.clinica.medical_system.model.Appointment;
import com.clinica.medical_system.model.Doctor;

@Service
public class ScheduleService {

    // 5 predefined doctors with 3 specialties
    private final List<Doctor> doctors = List.of(
            new Doctor("MED-001", "Dr. Carlos Ramirez", "Cardiology"),
            new Doctor("MED-002", "Dr. Laura Gomez", "Cardiology"),
            new Doctor("MED-003", "Dr. Andres Torres", "Pediatrics"),
            new Doctor("MED-004", "Dr. Sofia Herrera", "Pediatrics"),
            new Doctor("MED-005", "Dr. Miguel Ruiz", "General Medicine")
    );

    private final Map<String, Appointment> appointments = new HashMap<>();

    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctors.stream()
                .filter(d -> d.getSpecialty().equalsIgnoreCase(specialty))
                .collect(Collectors.toList());
    }

    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    public Appointment scheduleAppointment(String patientId, String doctorId, String date, String time) {
        Doctor doctor = doctors.stream()
                .filter(d -> d.getId().equals(doctorId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Doctor not found: " + doctorId));

        String appointmentId = "APT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Appointment appointment = new Appointment(appointmentId, patientId, doctorId, doctor.getName(),
                doctor.getSpecialty(), date, time);
        appointments.put(appointmentId, appointment);
        return appointment;
    }

    public Appointment cancelAppointment(String appointmentId) {
        Appointment appointment = appointments.get(appointmentId);
        if (appointment == null) {
            throw new NoSuchElementException("Appointment not found: " + appointmentId);
        }
        appointment.setStatus("CANCELLED");
        return appointment;
    }

    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointments.values().stream()
                .filter(a -> a.getPatientId().equals(patientId))
                .collect(Collectors.toList());
    }
}
