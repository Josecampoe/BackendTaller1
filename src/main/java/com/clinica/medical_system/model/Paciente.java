package com.clinica.medical_system.model;

import java.util.ArrayList;
import java.util.List;

public class Paciente {

    private String id;
    private String nombre;
    private String documento;
    private String email;
    private List<String> alergias;

    public Paciente() {
        this.alergias = new ArrayList<>();
    }

    public Paciente(String id, String nombre, String documento, String email) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.email = email;
        this.alergias = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getAlergias() { return alergias; }
    public void setAlergias(List<String> alergias) { this.alergias = alergias; }
}
