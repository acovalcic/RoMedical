package com.ady.romedical.model;

public class Clasament {
    private String doctor;
    private String spital;
    private double medie;

    public Clasament() {
    }

    public Clasament(String doctor, String spital, double medie) {
        this.doctor = doctor;
        this.spital = spital;
        this.medie = medie;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getSpital() {
        return spital;
    }

    public void setSpital(String spital) {
        this.spital = spital;
    }

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    @Override
    public String toString() {
        return "Clasament{" +
                "doctor='" + doctor + '\'' +
                ", spital='" + spital + '\'' +
                ", medie=" + medie +
                '}';
    }
}
