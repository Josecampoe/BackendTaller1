package com.clinica.medical_system.model;

public class LabResult {
    private String examName;
    private double value;
    private double minReference;
    private double maxReference;
    private String unit;

    public LabResult(String examName, double value, double minReference, double maxReference, String unit) {
        this.examName = examName;
        this.value = value;
        this.minReference = minReference;
        this.maxReference = maxReference;
        this.unit = unit;
    }

    public String getExamName() { return examName; }
    public double getValue() { return value; }
    public double getMinReference() { return minReference; }
    public double getMaxReference() { return maxReference; }
    public String getUnit() { return unit; }
    public boolean isNormal() { return value >= minReference && value <= maxReference; }
}