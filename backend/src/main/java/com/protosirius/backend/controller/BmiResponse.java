package com.protosirius.backend.controller;

import java.time.Instant;

import com.protosirius.backend.entity.Measure;

import jakarta.persistence.criteria.CriteriaBuilder.In;

public class BmiResponse {
    private int id;
    private int bmi;
    private String categorie;
    private Instant date;
    private double poidsKg;
    private double tailleCm;

    public static BmiResponse fromMeasure(Measure measure) {
        BmiResponse response = new BmiResponse();
        response.id = measure.getId();
        response.bmi = measure.getBmi();
        response.categorie = measure.getCategorie();
        response.date = measure.getDate();
        response.poidsKg = measure.getPoidsKg();
        response.tailleCm = measure.getTailleCm();
        return response;
    }

    public int getId() {
        return id;
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

    public double getPoidsKg() {
        return poidsKg;
    }

    public double getTailleCm() {
        return tailleCm;
    }



}
