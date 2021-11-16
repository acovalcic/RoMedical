package com.ady.romedical.model;

public class Categorie {
    private String denumire;

    public Categorie() {
    }

    public Categorie(String denumire) {
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
        return "Categorie{" +
                "denumire='" + denumire + '\'' +
                '}';
    }
}
