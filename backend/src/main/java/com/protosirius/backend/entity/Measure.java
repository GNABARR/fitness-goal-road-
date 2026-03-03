package com.protosirius.backend.entity;

import java.time.Instant;

import jakarta.persistence.PrePersist;

public class Measure {
    private int id;
    private double poidsKg;
    private double tailleCm;
    private int bmi;
    private String categorie;
    private Instant date;

    public Measure() {
    }

    public int getId() {
        return id;
    }

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = Instant.now();
        }
    }

    public double getPoidsKg() {
        return poidsKg;
    }          

    public double getTailleCm() {
        return tailleCm;
    }   

    public int getBmi() {
        return bmi;
    }

    public String getCategorie() {
        return categorie;
    }

    public Instant getDate() {
        return date;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setPoidsKg(double poidsKg) {
        this.poidsKg = poidsKg;
    }

    public void setTailleCm(double tailleCm) {
        this.tailleCm = tailleCm;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    

}
