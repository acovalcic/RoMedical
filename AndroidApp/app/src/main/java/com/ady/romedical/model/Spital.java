package com.ady.romedical.model;

public class Spital {
    private String denumire;
    private String judet;
    private String categorie;

    public Spital() {
    }

    public Spital(String denumire, String judet, String categorie) {
        this.denumire = denumire;
        this.judet = judet;
        this.categorie = categorie;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Spital{" +
                "denumire='" + denumire + '\'' +
                ", judet='" + judet + '\'' +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
