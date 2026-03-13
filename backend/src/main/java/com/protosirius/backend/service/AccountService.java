package com.protosirius.backend.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.protosirius.backend.entity.User;
import com.protosirius.backend.repository.UserRepository;

@Service
public class AccountService {
        private final UserRepository userRepository;

        private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

        public AccountService(UserRepository userRepository) {
                this.userRepository = userRepository;
        }


        public User getAccount(int userId) {
                return userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé , esseye de t'inscrie"));
        }

        public User updateAccount(int userId,String newEmail, String newPassword) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé , esseye de t'inscrie"));

                boolean haschanges=false;

                if (newEmail != null && !newEmail.isBlank()) {
                        String email= newEmail.trim();
                        
                        if (!email.matches(EMAIL_REGEX)) {
                                throw new IllegalArgumentException("format d'Email non valide I-I");
                        }

                        User Userexistant = userRepository.findByEmail(email);
                        if (Userexistant != null && Userexistant.getId() != user.getId()) {
                                throw new IllegalArgumentException("Email déjà utilisé par un autre compte");
                        }
                        
                        if (!email.equals(user.getEmail())) {
                                user.setEmail(email);
                                haschanges=true;
                        }
                }

                if (newPassword != null && !newPassword.isBlank()) {
                        String password = newPassword.trim();
                
                if (password.length() < 4) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le mot de passe doit contenir au moins 4 caractères");
                }

                user.setMotDePasse(password);
                haschanges=true;
                
        }
                if(!haschanges){
                        return user;
                }

                return userRepository.save(user); 
        
        }



}
