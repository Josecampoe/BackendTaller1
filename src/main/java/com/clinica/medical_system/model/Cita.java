package com.clinica.medical_system.model;

public class Cita {

    private String id;
    private String pacienteId;
    private String medicoId;
    private String medicoNombre;
    private String especialidad;
    private String fecha;
    private String estado;
    private String recordatorio;

    public Cita() {}

    public Cita(String id, String pacienteId, String medicoId, String medicoNombre,
                String especialidad, String fecha) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.medicoNombre = medicoNombre;
        this.especialidad = especialidad;
        this.fecha = fecha;
        this.estado = "ACTIVE";
        this.recordatorio = "Remember to arrive 15 minutes before your appointment on " + fecha;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }

    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }

    public String getMedicoNombre() { return medicoNombre; }
    public void setMedicoNombre(String medicoNombre) { this.medicoNombre = medicoNombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getRecordatorio() { return recordatorio; }
    public void setRecordatorio(String recordatorio) { this.recordatorio = recordatorio; }
}
