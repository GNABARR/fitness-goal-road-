package com.protosirius.backend.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@Table(name = "MesuresBMI")
public class Measure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private double poidsKg;

    @Column
    private double tailleCm;

    @Column
    private int bmi;

    @Column
    private String categorie;

    @Column
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
