package com.ady.romedical.model;

public class Judet {
    private String denumire;

    public Judet() {
    }

    public Judet(String denumire) {
        this.denumire = denumire;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        return "Judet{" +
                "denumire='" + denumire + '\'' +
                '}';
    }
}
