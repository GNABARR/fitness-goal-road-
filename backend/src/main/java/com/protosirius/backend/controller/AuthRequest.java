package com.protosirius.backend.controller;

import com.protosirius.backend.entity.User;

public class AuthRequest {
        private int id;
        private String nom;
        private String prenom;
        private String email;
        private String message;

        public AuthRequest() {
        }

        public AuthRequest(int id, String nom, String prenom, String email, String message) {
                this.id = id;
                this.nom = nom;
                this.prenom = prenom;
                this.email = email;
                this.message = message;
        }

        public static AuthRequest fromUser(User user, String message) {
                return new AuthRequest(user.getId(), user.getNom(), user.getPrenom(), user.getEmail(), message);
        }

        public int getId() {
                return id;
        }

        public String getNom() {
                return nom;
        }

        public String getPrenom() {
                return prenom;
        }

        public String getEmail() {
                return email;
        }

        public String getMessage() {
                return message;
        }
        
}
