package com.protosirius.backend.controller;

import com.protosirius.backend.entity.User;

public class AccountResponse {
    private int id;
    private String email;   
    private String message;
    private String nom;
    private String prenom;

    public static AccountResponse fromUser(User user) {
        AccountResponse response = new AccountResponse();
        response.id=user.getId();
        response.email=user.getEmail();
        response.nom=user.getNom();
        response.prenom=user.getPrenom();
        response.message="Infos du compte mises à jour";
        return response;
    }

    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getMessage() {
        return message;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
}
