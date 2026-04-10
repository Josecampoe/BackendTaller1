package com.clinica.medical_system.model;

public class Consulta {

    private String id;
    private String pacienteId;
    private String diagnostico;
    private String fecha;
    private String medico;

    public Consulta() {}

    public Consulta(String id, String pacienteId, String diagnostico, String fecha, String medico) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.diagnostico = diagnostico;
        this.fecha = fecha;
        this.medico = medico;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }
}
