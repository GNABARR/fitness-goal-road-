package com.fitness.DemoFitnessArtifact.controller;

import com.fitness.DemoFitnessArtifact.model.User;
import com.fitness.DemoFitnessArtifact.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
public ResponseEntity<User> createUser(@RequestBody User user) {
    if (user.getEmail() == null || user.getPassword() == null) {
        return ResponseEntity.badRequest().build();
    }
    if (userService.findByEmail(user.getEmail()).isPresent()) {
        return ResponseEntity.status(409).build();
    }
    User savedUser = userService.saveUser(user);
    return ResponseEntity.status(201).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userService.getUserById(id);
        if (user != null) {
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setWeight(userDetails.getWeight());
            user.setHeight(userDetails.getHeight());
            user.setActivityLevel(userDetails.getActivityLevel());
            user.setGoal(userDetails.getGoal());
            
            User updatedUser = userService.saveUser(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }
}
