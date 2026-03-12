package com.protosirius.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.protosirius.backend.controller.LoginRequest;
import com.protosirius.backend.controller.RegisterRequest;
import com.protosirius.backend.entity.User;
import com.protosirius.backend.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request){
        validateRegisterRequest(request);

        if (userRepository.existsByEmail(request.getEmail().trim())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email deja utilise");
        }

        User user = new User();
        user.setNom(request.getNom().trim());
        user.setPrenom(request.getPrenom().trim());
        user.setEmail(request.getEmail().trim());
        user.setMotDePasse(request.getPassword().trim());

        return userRepository.save(user); 
    }

    public User login(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email obligatoire");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mot de passe obligatoire");
        }

        User user = userRepository.findByEmail(request.getEmail().trim());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable");
        }

        if (!user.getMotDePasse().equals(request.getPassword().trim())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Mot de passe incorrect");
        }

        return user;
    }

    private void validateRegisterRequest(RegisterRequest request) {
        if (request.getNom() == null || request.getNom().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nom obligatoire");
        }

        if (request.getPrenom() == null || request.getPrenom().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prénom obligatoire");
        }

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email obligatoire");
        }

        if (!request.getEmail().trim().matches(EMAIL_REGEX)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Format email invalide");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mot de passe obligatoire");
        }

        if (request.getPassword().trim().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mot de passe trop court");
        }
    }
}


