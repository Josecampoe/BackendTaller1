package com.clinica.medical_system.facade;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.clinica.medical_system.model.Cita;
import com.clinica.medical_system.model.Consulta;
import com.clinica.medical_system.model.Paciente;
import com.clinica.medical_system.service.AgendaService;
import com.clinica.medical_system.service.HistoriaClinicaService;
import com.clinica.medical_system.service.LaboratorioService;
import com.clinica.medical_system.service.PacienteService;
import com.clinica.medical_system.service.PrescripcionService;

/**
 * ClinicaFacade — Facade pattern implementation.
 *
 * Provides a simplified, unified interface to the patient portal operations,
 * hiding the complexity of the underlying subsystems from the client.
 *
 * Each method coordinates at least 2 subsystems internally.
 */
@Component
public class ClinicaFacade {

    private final PacienteService pacienteService;
    private final AgendaService agendaService;
    private final HistoriaClinicaService historiaClinicaService;
    private final PrescripcionService prescripcionService;
    private final LaboratorioService laboratorioService;

    public ClinicaFacade(PacienteService pacienteService,
                         AgendaService agendaService,
                         HistoriaClinicaService historiaClinicaService,
                         PrescripcionService prescripcionService,
                         LaboratorioService laboratorioService) {
        this.pacienteService = pacienteService;
        this.agendaService = agendaService;
        this.historiaClinicaService = historiaClinicaService;
        this.prescripcionService = prescripcionService;
        this.laboratorioService = laboratorioService;
    }

    /**
     * Schedules an appointment for a patient.
     * Subsystems: PacienteService (validate patient exists) + AgendaService (schedule)
     */
    public Cita agendarCita(String pacienteId, String medicoId, String fecha) {
        // Subsystem 1: validate patient exists
        pacienteService.consultarPerfil(pacienteId);
        // Subsystem 2: schedule appointment
        return agendaService.agendarCita(pacienteId, medicoId, fecha);
    }

    /**
     * Returns the complete medical history of a patient.
     * Subsystems: HistoriaClinicaService + PrescripcionService + LaboratorioService
     */
    public Map<String, Object> verHistoriaCompleta(String pacienteId) {
        // Subsystem 1: validate patient and get profile
        Paciente paciente = pacienteService.consultarPerfil(pacienteId);

        // Subsystem 2: past appointments
        List<Cita> citas = agendaService.obtenerCitasPorPaciente(pacienteId);

        // Subsystem 3: clinical history
        List<Consulta> consultas = historiaClinicaService.consultarHistoria(pacienteId);

        // Subsystem 4: prescriptions
        List<Map<String, Object>> prescripciones = prescripcionService.obtenerPrescripcionesPorPaciente(pacienteId);

        // Subsystem 5: lab results
        List<Map<String, Object>> laboratorios = laboratorioService.obtenerResultadosPorPaciente(pacienteId);

        Map<String, Object> historia = new LinkedHashMap<>();
        historia.put("patient", paciente);
        historia.put("appointments", citas);
        historia.put("consultations", consultas);
        historia.put("prescriptions", prescripciones);
        historia.put("labResults", laboratorios);
        return historia;
    }

    /**
     * Generates a prescription for a patient, validating allergies first.
     * Subsystems: HistoriaClinicaService (allergy check) + PrescripcionService (generate)
     */
    public Map<String, Object> generarPrescripcion(String pacienteId, List<Map<String, String>> medicamentos) {
        // Subsystem 1: check patient allergies
        List<String> alergias = historiaClinicaService.consultarAlergias(pacienteId, pacienteService);

        // Validate no medication conflicts with known allergies
        for (Map<String, String> med : medicamentos) {
            String nombre = med.getOrDefault("name", "").toLowerCase();
            for (String alergia : alergias) {
                if (nombre.contains(alergia.toLowerCase())) {
                    throw new IllegalArgumentException(
                            "Medication '" + med.get("name") + "' conflicts with patient allergy: " + alergia);
                }
            }
        }

        // Subsystem 2: generate prescription
        return prescripcionService.generarPrescripcion(pacienteId, medicamentos);
    }

    /**
     * Requests lab exams for a patient and registers them in clinical history.
     * Subsystems: LaboratorioService (run exams) + HistoriaClinicaService (register)
     */
    public List<Map<String, Object>> solicitarExamenes(String pacienteId, List<String> examenes) {
        // Subsystem 1: validate patient exists
        pacienteService.consultarPerfil(pacienteId);

        // Subsystem 2: run lab exams
        List<Map<String, Object>> resultados = laboratorioService.solicitarExamenes(pacienteId, examenes);

        // Subsystem 3: register in clinical history
        historiaClinicaService.registrarConsulta(pacienteId,
                "Lab exams requested: " + String.join(", ", examenes),
                "Laboratory System");

        return resultados;
    }
}
