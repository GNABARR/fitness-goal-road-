package com.protosirius.backend.controller;

import com.protosirius.backend.entity.Motd;
import com.protosirius.backend.service.MotdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    private final MotdService motdService;

    public HelloController(MotdService motdService) {
        this.motdService = motdService;
    }

    @GetMapping("/motd")
    public ResponseEntity<Motd> getMotd() {
        log.info("GET /api/hello/motd");
        return ResponseEntity.ok(motdService.getMotd());
    }

    @PutMapping("/motd")
    public ResponseEntity<Motd> updateMotd(@RequestBody Motd motdRequest) {
        log.info("PUT /api/hello/motd - message: {}", motdRequest.getMessage());
        return ResponseEntity.ok(motdService.updateMotd(motdRequest.getMessage()));
    }
}
