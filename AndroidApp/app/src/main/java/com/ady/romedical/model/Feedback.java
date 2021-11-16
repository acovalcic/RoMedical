package com.ady.romedical.model;

public class Feedback {
    private String spital;
    private String doctor;
    private int notaSpital;
    private int notaDoctor;
    private String mentiuni;

    public Feedback() {
    }

    public Feedback(String spital, String doctor, int notaSpital, int notaDoctor, String mentiuni) {
        this.spital = spital;
        this.doctor = doctor;
        this.notaSpital = notaSpital;
        this.notaDoctor = notaDoctor;
        this.mentiuni = mentiuni;
    }

    public String getSpital() {
        return spital;
    }

    public void setSpital(String spital) {
        this.spital = spital;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public int getNotaSpital() {
        return notaSpital;
    }

    public void setNotaSpital(int notaSpital) {
        this.notaSpital = notaSpital;
    }

    public int getNotaDoctor() {
        return notaDoctor;
    }

    public void setNotaDoctor(int notaDoctor) {
        this.notaDoctor = notaDoctor;
    }

    public String getMentiuni() {
        return mentiuni;
    }

    public void setMentiuni(String mentiuni) {
        this.mentiuni = mentiuni;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "spital='" + spital + '\'' +
                ", doctor='" + doctor + '\'' +
                ", notaSpital=" + notaSpital +
                ", notaDoctor=" + notaDoctor +
                ", mentiuni='" + mentiuni + '\'' +
                '}';
    }
}
