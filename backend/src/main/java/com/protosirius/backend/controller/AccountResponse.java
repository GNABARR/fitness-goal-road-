package com.protosirius.backend.controller;

import com.protosirius.backend.entity.User;

public class AccountResponse {
    private int id;
    private String email;   
    private String message;

    public static AccountResponse fromUser(User user) {
        AccountResponse response = new AccountResponse();
        response.id=user.getId();
        response.email=user.getEmail();
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
}
